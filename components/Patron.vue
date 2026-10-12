<script setup lang="ts">
// Una tarjeta del catálogo de patrones. Uso en las diapositivas:
//   <Patron n="01" name="Factory Method" ex="Calendar.getInstance()" code hoy>Descripción</Patron>
// - ex:   ejemplo corto que aparece abajo (code = se muestra como código)
// - hoy:  marca el patrón que se explica en la charla
// El dibujo se elige por el nombre del patrón.
import { computed } from 'vue'

const props = defineProps<{
  n: string
  name: string
  ex?: string
  code?: boolean
  hoy?: boolean
}>()

// Dibujos de 48×30. Clases: l = línea, al = línea de acento, a = relleno de acento,
// d = punteada, b = rellena con el fondo (tapa las líneas), w = línea del color del fondo.
const icons: Record<string, string> = {
  // creacionales
  factorymethod: `
    <rect class="l" x="2" y="8" width="14" height="14" rx="1.5"/>
    <path class="l" d="M18 15h8M23 12l3 3-3 3"/>
    <rect class="l d" x="34" y="4" width="12" height="12" rx="1.5"/>
    <rect class="a" x="30" y="10" width="12" height="12" rx="1.5"/>`,
  abstractfactory: `
    <rect class="l" x="2" y="8" width="12" height="14" rx="1.5"/>
    <path class="l" d="M15 12l7-4M15 18l7 4"/>
    <circle class="a" cx="28" cy="7" r="4"/>
    <rect class="a" x="36" y="3" width="8" height="8" rx="1"/>
    <circle class="l" cx="28" cy="23" r="4"/>
    <rect class="l" x="36" y="19" width="8" height="8" rx="1"/>`,
  builder: `
    <rect class="l" x="4" y="22" width="40" height="6" rx="1"/>
    <rect class="l" x="10" y="14" width="28" height="6" rx="1"/>
    <rect class="a" x="16" y="6" width="16" height="6" rx="1"/>
    <rect class="l d" x="20" y="0.5" width="8" height="3.5" rx="1"/>`,
  prototype: `
    <rect class="l" x="3" y="5" width="16" height="20" rx="1.5"/>
    <path class="l" d="M7 11h8M7 15h8M7 19h5"/>
    <path class="al" d="M21 15h5M24 12.5l2.5 2.5-2.5 2.5"/>
    <rect class="al d" x="29" y="5" width="16" height="20" rx="1.5"/>
    <path class="al" d="M33 11h8M33 15h8M33 19h5"/>`,
  singleton: `
    <circle class="l" cx="14" cy="15" r="11"/>
    <circle class="a" cx="14" cy="15" r="6"/>
    <circle class="l d" cx="38" cy="15" r="7"/>
    <path class="l" d="M32 21l12-12"/>`,
  // estructurales
  adapter: `
    <path class="l" d="M2 8h10v4h4v6h-4v4H2z"/>
    <rect class="a" x="18" y="10" width="12" height="10" rx="1"/>
    <path class="l" d="M46 8H34v4a3 3 0 0 1 0 6v4h12z"/>`,
  bridge: `
    <path class="al thick" d="M16 7h16"/>
    <path class="l" d="M9 11v5M5 16h8M5 16v3M13 16v3M39 11v5M35 16h8M35 16v3M43 16v3"/>
    <rect class="l" x="2" y="3" width="14" height="8" rx="1.5"/>
    <rect class="l" x="32" y="3" width="14" height="8" rx="1.5"/>
    <rect class="l" x="2" y="19" width="6" height="7" rx="1"/>
    <rect class="l" x="10" y="19" width="6" height="7" rx="1"/>
    <rect class="l" x="32" y="19" width="6" height="7" rx="1"/>
    <rect class="l" x="40" y="19" width="6" height="7" rx="1"/>`,
  composite: `
    <path class="l" d="M24 9v4M8 13h32M8 13v4M24 13v4M40 13v4"/>
    <rect class="a" x="18" y="2" width="12" height="7" rx="1.5"/>
    <rect class="l" x="3" y="17" width="10" height="9" rx="1.5"/>
    <rect class="l" x="16" y="17" width="16" height="11" rx="1.5"/>
    <rect class="l" x="18.5" y="20" width="5" height="5" rx="0.8"/>
    <rect class="l" x="24.5" y="20" width="5" height="5" rx="0.8"/>
    <rect class="l" x="35" y="17" width="10" height="9" rx="1.5"/>`,
  decorator: `
    <rect class="l" x="5" y="1.5" width="38" height="27" rx="3"/>
    <rect class="l" x="11" y="6.5" width="26" height="17" rx="2"/>
    <rect class="a" x="17" y="11" width="14" height="8" rx="1"/>`,
  facade: `
    <path class="l" d="M2 15h8M7 12l3 3-3 3"/>
    <rect class="a" x="13" y="2" width="5" height="26" rx="1"/>
    <rect class="l" x="23" y="3" width="9" height="6" rx="1"/>
    <rect class="l" x="36" y="3" width="9" height="6" rx="1"/>
    <rect class="l" x="23" y="12" width="9" height="6" rx="1"/>
    <rect class="l" x="36" y="12" width="9" height="6" rx="1"/>
    <rect class="l" x="23" y="21" width="9" height="6" rx="1"/>
    <rect class="l" x="36" y="21" width="9" height="6" rx="1"/>`,
  flyweight: `
    <path class="l" d="M6 6L20 21M15 6L22 21M24 6V21M33 6L26 21M42 6L28 21"/>
    <circle class="l b" cx="6" cy="5" r="2.6"/>
    <circle class="l b" cx="15" cy="5" r="2.6"/>
    <circle class="l b" cx="24" cy="5" r="2.6"/>
    <circle class="l b" cx="33" cy="5" r="2.6"/>
    <circle class="l b" cx="42" cy="5" r="2.6"/>
    <rect class="a" x="16" y="20" width="16" height="7" rx="1.5"/>`,
  proxy: `
    <circle class="l" cx="5" cy="15" r="3"/>
    <path class="l" d="M9 15h3.5M10.5 13l2 2-2 2"/>
    <rect class="a" x="15" y="9" width="8" height="12" rx="1"/>
    <path class="l" d="M24.5 15h3.5M26 13l2 2-2 2"/>
    <rect class="l" x="30" y="4" width="16" height="22" rx="1.5"/>`,
  // de comportamiento
  chainofresponsibility: `
    <path class="l" d="M10 15h4M22 15h4M34 15h4"/>
    <rect class="l" x="2" y="11" width="8" height="8" rx="1.5"/>
    <rect class="l" x="14" y="11" width="8" height="8" rx="1.5"/>
    <rect class="a" x="26" y="11" width="8" height="8" rx="1.5"/>
    <rect class="l d" x="38" y="11" width="8" height="8" rx="1.5"/>`,
  command: `
    <path class="a" d="M4 3h13l5 5v19H4z"/>
    <path class="w" d="M17 3v5h5M8 13h10M8 17h10M8 21h6"/>
    <path class="l" d="M25 15h7M29.5 12.5l2.5 2.5-2.5 2.5"/>
    <rect class="l" x="35" y="9" width="11" height="12" rx="1.5"/>`,
  interpreter: `
    <path class="l" d="M21 5L10 15M21 5l11 10M32 15l-7 10.5M32 15l7 10.5"/>
    <circle class="a" cx="21" cy="5" r="3.6"/>
    <circle class="l b" cx="10" cy="15" r="3.6"/>
    <circle class="a" cx="32" cy="15" r="3.6"/>
    <circle class="l b" cx="25" cy="25.5" r="3.6"/>
    <circle class="l b" cx="39" cy="25.5" r="3.6"/>`,
  iterator: `
    <rect class="l" x="2" y="5" width="7" height="9" rx="1"/>
    <rect class="l" x="11" y="5" width="7" height="9" rx="1"/>
    <rect class="a" x="20" y="5" width="7" height="9" rx="1"/>
    <rect class="l" x="29" y="5" width="7" height="9" rx="1"/>
    <rect class="l" x="38" y="5" width="7" height="9" rx="1"/>
    <path class="a" d="M23.5 18L27 24H20z"/>`,
  mediator: `
    <path class="l" d="M6 5l16 8M42 5l-16 8M6 25l16-8M42 25l-16-8"/>
    <circle class="l b" cx="6" cy="5" r="3"/>
    <circle class="l b" cx="42" cy="5" r="3"/>
    <circle class="l b" cx="6" cy="25" r="3"/>
    <circle class="l b" cx="42" cy="25" r="3"/>
    <rect class="a" x="18" y="9" width="12" height="12" rx="2"/>`,
  memento: `
    <path class="l" d="M14 3h16l5 5v19H14z"/>
    <rect class="a" x="18" y="3" width="10" height="7" rx="0.5"/>
    <rect class="l" x="18" y="16" width="13" height="11" rx="0.5"/>`,
  observer: `
    <path class="l" d="M13 15L35 5M13 15h22M13 15l22 10"/>
    <circle class="a" cx="8" cy="15" r="5.5"/>
    <circle class="l b" cx="38" cy="5" r="3.5"/>
    <circle class="l b" cx="39" cy="15" r="3.5"/>
    <circle class="l b" cx="38" cy="25" r="3.5"/>`,
  state: `
    <rect class="l" x="17" y="1" width="14" height="28" rx="4"/>
    <circle class="l" cx="24" cy="7.5" r="3.2"/>
    <circle class="a" cx="24" cy="15" r="3.2"/>
    <circle class="l" cx="24" cy="22.5" r="3.2"/>`,
  strategy: `
    <path class="l d" d="M12 15L37 5M12 15l25 10"/>
    <path class="al thick" d="M12 15h25"/>
    <rect class="l" x="2" y="10" width="10" height="10" rx="1.5"/>
    <rect class="l" x="38" y="2" width="7" height="6" rx="1"/>
    <rect class="a" x="38" y="12" width="7" height="6" rx="1"/>
    <rect class="l" x="38" y="22" width="7" height="6" rx="1"/>`,
  templatemethod: `
    <rect class="l" x="10" y="2" width="28" height="5" rx="1"/>
    <rect class="a" x="10" y="9" width="28" height="5" rx="1"/>
    <rect class="l" x="10" y="16" width="28" height="5" rx="1"/>
    <rect class="a" x="10" y="23" width="28" height="5" rx="1"/>`,
  visitor: `
    <rect class="l" x="3" y="18" width="10" height="9" rx="1.5"/>
    <rect class="l" x="19" y="18" width="10" height="9" rx="1.5"/>
    <rect class="l" x="35" y="18" width="10" height="9" rx="1.5"/>
    <path class="al d" d="M8 15Q16 1 24 15Q32 1 40 15"/>
    <path class="al" d="M36.3 13.1L40 15l0.2-4.1"/>
    <circle class="a" cx="8" cy="15" r="2"/>`,
}

const icon = computed(() => icons[props.name.toLowerCase().replace(/[^a-z]/g, '')] ?? '')
</script>

<template>
  <div class="pt" :class="{ 'pt-hoy': hoy }">
    <div class="pt-top">
      <svg class="pt-ico" viewBox="0 0 48 30" aria-hidden="true" v-html="icon" />
      <span class="pt-num"><span v-if="hoy" class="tag">hoy</span>{{ n }}</span>
    </div>
    <div class="pt-name">{{ name }}</div>
    <div class="pt-desc"><slot /></div>
    <div v-if="ex" class="pt-ex" :class="{ 'pt-code': code }">{{ ex }}</div>
  </div>
</template>

// Dibuja los diagramas Mermaid con la paleta del modo actual (claro u oscuro).
// Slidev vuelve a llamar a esta función cuando se cambia de modo, así que los
// diagramas siguen a style.css sin tener colores escritos en cada diapositiva.
// En los diagramas basta con marcar los nodos destacados: `class A,B acc`.

import { defineMermaidRendererSetup } from '@slidev/types'
import mermaid from 'mermaid/dist/mermaid.esm.mjs'

const fontFamily = 'Segoe UI, Helvetica, Arial, sans-serif'

const paletas = {
  light: {
    themeVariables: {
      fontFamily,
      primaryColor: '#ffffff',
      primaryBorderColor: '#16161a',
      primaryTextColor: '#16161a',
      secondaryColor: '#f1efe9',
      tertiaryColor: '#ffffff',
      lineColor: '#6b6b70',
      textColor: '#16161a',
      mainBkg: '#ffffff',
      nodeBorder: '#16161a',
      clusterBkg: '#f1efe9',
      clusterBorder: '#e3e0d9',
      edgeLabelBackground: '#faf9f6',
    },
    acc: 'fill:#fbe3da,stroke:#e4572e,stroke-width:2px,color:#16161a',
  },
  dark: {
    themeVariables: {
      fontFamily,
      primaryColor: '#18181b',
      primaryBorderColor: '#d4d4d8',
      primaryTextColor: '#ececec',
      secondaryColor: '#202024',
      tertiaryColor: '#18181b',
      lineColor: '#9a9aa0',
      textColor: '#ececec',
      mainBkg: '#18181b',
      nodeBorder: '#d4d4d8',
      clusterBkg: '#18181b',
      clusterBorder: '#2a2a2e',
      edgeLabelBackground: '#0f0f11',
    },
    acc: 'fill:#3a1e14,stroke:#ff6b3d,stroke-width:2px,color:#ececec',
  },
}

let contador = 0

export default defineMermaidRendererSetup(() => async (code, options) => {
  const paleta = options.theme === 'dark' ? paletas.dark : paletas.light

  mermaid.initialize({
    startOnLoad: false,
    theme: 'base',
    themeVariables: paleta.themeVariables,
  })

  const id = `mermaid-diagrama-${contador++}`
  const contenedor = document.getElementById('mermaid-rendering-container') ?? undefined
  const { svg } = await mermaid.render(id, `${code}\n  classDef acc ${paleta.acc}`, contenedor)
  return svg
})

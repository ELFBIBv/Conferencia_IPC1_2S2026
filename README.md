# Diseñar antes de Codificar

Charla introductoria sobre patrones de diseño y arquitectura para proyectos escalables, pensada para estudiantes que están aprendiendo a programar en Java.

Las diapositivas están hechas con [Slidev](https://sli.dev): se escriben en Markdown (`presentacion.md`) y se ven en el navegador.

## Requisitos

- [Node.js](https://nodejs.org) 20 o superior (incluye `npm`)

## Instalación

Solo la primera vez, desde la carpeta del proyecto:

```bash
npm install
```

## Ver la presentación

```bash
npm run dev
```

Se abre en el navegador en <http://localhost:3030>. Cada vez que guardes `presentacion.md` o `style.css`, la presentación se actualiza sola.

| Dirección                          | Para qué sirve                                        |
| ---------------------------------- | ----------------------------------------------------- |
| `http://localhost:3030`            | La presentación                                       |
| `http://localhost:3030/presenter`  | Modo presentador: notas, siguiente diapositiva y reloj |
| `http://localhost:3030/overview`   | Todas las diapositivas en una sola página             |
| `http://localhost:3030/export`     | Exportar a PDF o PNG desde el navegador               |

### Atajos de teclado

| Tecla                 | Acción                         |
| --------------------- | ------------------------------ |
| `→` / `Espacio`       | Siguiente                      |
| `←`                   | Anterior                       |
| `o`                   | Vista general de diapositivas  |
| `d`                   | Cambiar entre claro y oscuro   |
| `F11`                 | Pantalla completa (navegador)  |
| `g`                   | Ir a una diapositiva           |

## Modo claro y oscuro

La presentación usa `colorSchema: auto`: arranca en el modo que tenga el sistema operativo y se puede cambiar en cualquier momento con la tecla `d` o con el ícono de luna de la barra inferior.

Los colores de cada modo están en `style.css` (`:root` para claro, `html.dark` para oscuro). Los diagramas Mermaid toman sus colores de `setup/mermaid-renderer.ts`; para resaltar un nodo en naranja basta con escribir `class Nodo acc` dentro del diagrama.

## Exportar a PDF

La forma más simple es con el servidor corriendo (`npm run dev`):

1. Abrir <http://localhost:3030/export>.
2. Elegir el modo (**Light** o **Dark**) y el rango de diapositivas.
3. Descargar el PDF.

Desde la terminal también se puede, pero la primera vez hay que instalar el navegador que usa Slidev para exportar:

```bash
npm install -D playwright-chromium
npm run export                          # genera presentacion-export.pdf
npm run export -- --dark                # versión oscura
```

## Publicarla como página web

```bash
npm run build
```

Genera la carpeta `dist/`, que se puede subir a cualquier hosting estático (GitHub Pages, Netlify, Vercel…).

## Estructura

| Archivo / carpeta              | Contenido                                                        |
| ------------------------------ | ---------------------------------------------------------------- |
| `presentacion.md`              | Las diapositivas (separadas por `---`) y las notas del presentador (`<!-- … -->`) |
| `style.css`                    | Estilos y colores de la presentación                             |
| `components/Patron.vue`        | Tarjeta del catálogo de patrones                                 |
| `setup/mermaid-renderer.ts`    | Colores de los diagramas en modo claro y oscuro                  |
| `Factory_method/`              | Ejemplo en Java del patrón Factory Method que se ve en la charla |
| `OLC1_Proyecto2_202203856/`    | Proyecto del intérprete usado como caso real (tiene su propio README) |

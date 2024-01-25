import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
    html, body, div, span, applet, object, iframe,
    h1, h2, h3, h4, h5, h6, p, blockquote, pre,
    a, abbr, acronym, address, big, cite, code,
    del, dfn, em, img, ins, kbd, q, s, samp,
    small, strike, strong, sub, sup, tt, var,
    b, u, i, center,
    dl, dt, dd, ol, ul, li,
    fieldset, form, label, legend,
    table, caption, tbody, tfoot, thead, tr, th, td,
    article, aside, canvas, details, embed, 
    figure, figcaption, footer, header, hgroup, 
    menu, nav, output, ruby, section, summary,
    time, mark, audio, video {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        font-family: 'YdestreetL', sans-serif;
        vertical-align: baseline;
        box-sizing: border-box;
    }
    /* HTML5 display-role reset for older browsers */
    article, aside, details, figcaption, figure, 
    footer, header, hgroup, menu, nav, section {
        display: block;
    }
    body {
        line-height: 1;
    }
    ol, ul {
        list-style: none;
    }
    blockquote, q {
        quotes: none;
    }
    blockquote:before, blockquote:after,
    q:before, q:after {
        content: '';
        content: none;
    }
    table {
        border-collapse: collapse;
        border-spacing: 0;
    }

    @font-face {
        font-family: 'YdestreetL';
        src: url('/fonts/YdestreetL.otf') format('opentype'),
             url('/fonts/YdestreetL.ttf') format('truetype');
        
    }
    @font-face {
        font-family: 'YdestreetB';
        src: url('/fonts/YdeStreetB.otf') format('opentype'),
             url('/fonts/YdestreetB.ttf') format('truetype');
    }
    button{
        font-family: 'YdestreetL', sans-serif
    }
    :root {
      --rp-black: #0d0d0d;
      --rp-white: #fafafa;
      --rp-yellow: #fee800;
    }

`;
export default GlobalStyles;

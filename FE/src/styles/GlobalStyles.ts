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
    time, mark, audio, video, input, svg, button {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        font: inherit;
        font-family: 'YdestreetL', sans-serif;
        color: var(--rp-white);
        vertical-align: baseline;
        box-sizing: border-box;
    }
    /* HTML5 display-role reset for older browsers */
    article, aside, details, figcaption, figure, 
    footer, header, hgroup, menu, nav, section {
        display: block;
    }
    html, body, #root {
        width: 100%;
        height: 100%;
        margin: 0;
    }
    body {
        line-height: 16px;
        background-color: var(--background-color);
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
    button, input{
        font-family: 'YdestreetL', sans-serif;
        background-color: transparent;
        white-space: nowrap;
    }
    @font-face {
        font-family: 'Pretendard';
        src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
        font-style: normal;
    }
    button:hover {
        cursor: pointer;
    }
    :root {
      --color-red: #f4361e;
      --rp-black: #0d0d0d;
      --rp-white: #f1f1f1;
      --rp-yellow: #fee800;
      --rp-grey-300: #aaa;
      --rp-grey-500: #525252;
      --rp-grey-600: #353637;
      --rp-grey-700: #272829;
      --rp-grey-800: #1e1e20;
      --rp-orange: #ffac00;
      --background-color: var(--rp-grey-800);
      --sidebar-color: var(--rp-grey-700);
      --sidebar-nav-hover: var(--rp-grey-600);
      --sidebar-nav-active: var(--rp-grey-500);
      --sidebar-nav-height: 2.8rem;
      --sidebar-nav-padding: 0.8rem;
      --text-primary-dark-mode: #f1f1f1;
      --text-secondary-dark-mode: #aaa;
      --text-secondary: #606060;
      --sidebar: 240px;
      --sidebar-margin: 12px;
      --searchbar-height: 40px;
      --searchbar-width: 480px;
      --searchbar-community-padding: 12px;
      --border-radius-small: 5px;
      --menu-color: var(--rp-grey-700);
      --menu-hover-color: var(--rp-grey-600);
      --menu-button-size: 2rem;
      --sidebar-project--width:620px;
      --sidebar-project--width-sm:400px;
      /* button */
      --button-padding: 4px 16px;
      --button-padding-small: 4px 12px;
      --button-border-radius: 20px;
      --button-icon-margin: 4px;

      --button-square: 36px;
      --button-square-margin: 12px;

      /* font family  */
      --font-family-primary: 'YdestreetL';

      /* font size */
      --font-size-s: 0.8rem;
      --font-size-l: 1.2rem;
      --font-size-xl: 1.5rem;

      /* box shadow */
      --box-shadow: inset 0 0 0.5px 1px hsla(0, 0%, 100%, 0.075),
        0 0 0 1px hsla(0, 0%, 0%, 0.05), 0 0.3px 0.4px hsla(0, 0%, 0%, 0.02),
        0 0.9px 1.5px hsla(0, 0%, 0%, 0.045), 0 3.5px 6px hsla(0, 0%, 0%, 0.09);

      /* text shadow */
      --text-shadow: 0 1px 4px rgba(0, 0, 0, 0.1), 0 2px 8px rgba(0, 0, 0, 0.15),
        0 4px 16px rgba(0, 0, 0, 0.2), 0 6px 24px rgba(0, 0, 0, 0.25);

      /* homepage */
      --homepage-padding: 48px;
      --homepage-grid-row-size: 3rem;

      /* menu tab */
      --menu-tab-margin-top: 48px;
    }
    .red {
        color: var(--color-red);
    }

    /* 스크롤바 스타일 */
    ::-webkit-scrollbar {
        width: 6px; /* 스크롤바 너비 */
    }

    ::-webkit-scrollbar-track {
        background: #f1f1f1; /* 스크롤바 배경색 */
    }

    ::-webkit-scrollbar-thumb {
        background: #888; /* 스크롤바 색상 */
    }

    ::-webkit-scrollbar-thumb:hover {
        background: #555; /* 스크롤바를 마우스로 가리킬 때의 색상 */
    }


`;
export default GlobalStyles;

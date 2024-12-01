import { LitElement, html, css } from "lit";


export class TranslatePage extends LitElement {
    static properties = {
      selectedLanguage: { type: String },
      translatedText: { type: String },
    };
  
    constructor() {
      super();
      this.selectedLanguage = "es";
      this.translatedText = "";
    }
  
    // Método para manejar la traducción
    async translatePage(event) {
      const { language, content } = event.detail;
      try {
        console.log(`https://libretranslate.com/?source=auto&target=${language}&q=${content}`)
        const res = await fetch(`https://libretranslate.com/?source=auto&target=${language}&q=${content}`);

        console.log(res.text()); // Aquí revisa el resultado de la traducción
        this.translatedText = res.translatedText;
      } catch (error) {
        console.error("Error al traducir:", error);
      }
      
    }
  
    connectedCallback() {
      super.connectedCallback();
      window.addEventListener("language-selected", (e) => this.translatePage(e));
    }
  
    disconnectedCallback() {
      window.removeEventListener("language-selected", this.translatePage);
      super.disconnectedCallback();
    }
  
    render() {
      return html`
        <div>
          <h2>Contenido traducido:</h2>
          <div>
            ${this.translatedText
              ? html`${this.translatedText}`
              : html`<p>Selecciona un idioma para traducir el contenido.</p>`}
          </div>
        </div>
      `;
    }
  }
  
  customElements.define("translate-page", TranslatePage);
  
import { LitElement, html, css } from "lit";
import { apiKey2 } from "../utils/config.js";

export class TranslatePage extends LitElement {
  static properties = {
    selectedLanguage: { type: String },
    translatedText: { type: String },
  };

  constructor() {
    super();
    this.selectedLanguage = "es"; // Idioma por defecto
    this.translatedText = ""; // Contenido traducido
  }

  // Método para manejar la traducción
  async translatePage(event) {
    const { language, content } = event.detail;
    const apiUrl = "https://api-free.deepl.com/v2/translate";

    try {
      const response = await fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
          auth_key: apiKey2,
          text: decodeURIComponent(content),
          target_lang: language,
          tag_handling: "html",
        }),
      });

      if (!response.ok) {
        throw new Error(`Error de traducción: ${response.statusText}`);
      }

      const data = await response.json();
      this.translatedText = data.translations[0].text;

      // Notificar a otros componentes que la traducción está lista
      this.sendContentTranslated();
    } catch (error) {
      console.error("Error al traducir:", error);
      this.translatedText = "Hubo un error al realizar la traducción.";
    }
  }

  // Método para notificar que el contenido traducido está listo
  sendContentTranslated() {
    const event = new CustomEvent("text-translated", {
      detail: {
        content: this.translatedText,
      },
      bubbles: true,
      composed: true,
    });
    this.dispatchEvent(event);
  }

  // Escuchar eventos cuando se conecta el componente
  connectedCallback() {
    super.connectedCallback();
    window.addEventListener("language-selected", (e) => this.translatePage(e));
  }

  // Desconectar eventos cuando el componente se desconecta
  disconnectedCallback() {
    window.removeEventListener("language-selected", (e) => this.translatePage(e));
    super.disconnectedCallback();
  }

  /*render() {
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
  }*/
}

customElements.define("translate-page", TranslatePage);

import { LitElement, html, css } from "lit";
import { apiKey } from '../utils/config.js';

export class GetWeather extends LitElement {
  static styles = css`
    /* Fondo general */
    :host {
      display: block;
      background: linear-gradient(to bottom, #85c1e9, #2874a6);
      color: #ffffff;
      padding: 20px;
      border-radius: 12px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
      text-align: center;
      width: 300px;
      margin: auto;
      font-family: 'Arial', sans-serif;
    }

    /* Input field */
    input {
      width: 90%;
      padding: 10px;
      margin-bottom: 10px;
      border: none;
      border-radius: 5px;
      font-size: 16px;
    }

    /* Botón */
    button {
      background-color: #3498db;
      color: #ffffff;
      padding: 10px 15px;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      cursor: pointer;
      transition: background-color 0.3s ease, transform 0.2s;
    }

    button:hover {
      background-color: #21618c;
      transform: scale(1.05);
    }

    /* Clima general */
    .weather-container {
      margin-top: 15px;
    }

    /* Icono del clima */
    .weather-icon {
      width: 80px;
      height: 80px;
      margin: 10px auto;
    }

    /* Descripción */
    .description {
      text-transform: capitalize;
      font-size: 18px;
      font-weight: bold;
    }

    /* Temperatura */
    .temperature {
      font-size: 36px;
      font-weight: bold;
      margin: 10px 0;
    }

    /* Detalles */
    .details {
      font-size: 14px;
    }
  `;

  static properties = {
    latitude: { type: Number },
    longitude: { type: Number },
    location: { type: String },
    weather: { type: Object },
    errorMessage: { type: String },
    webContent: {type: String},
  };

  constructor() {
    super();
    this.latitude = 41.3828939; // Barcelona por defecto
    this.longitude = 2.1774322;
    this.location = "Barcelona";
    this.weather = null;
    this.errorMessage = null;
    this.webContent = ""
  }


  // Obtiene las coordenadas de la ubicación
  getWeatherMap() {
    this.weather = null;
    this.errorMessage = null;
    this.webContent = "";
    this.requestUpdate();
    fetch(
      `http://api.openweathermap.org/geo/1.0/direct?q=${this.location}&limit=5&appid=${apiKey}`
    )
      .then((data) => data.json())
      .then((res) => {
        if (res.length > 0) {
          this.latitude = res[0].lat;
          this.longitude = res[0].lon;
          this.getWeather(); // Ahora busca el clima con las coordenadas
        } else {
          this.errorMessage = "No se encontraron resultados para la ubicación.";
          this.weather = null;
        }
      })
      .catch((error) => {
        this.errorMessage = `Error al obtener la geolocalización: ${error.message}`;
      });
  }

  // Obtiene el clima
  getWeather() {
    this.weather = null;
    this.errorMessage = null;
    this.webContent = "";
    this.requestUpdate();
    fetch(
      `https://api.openweathermap.org/data/2.5/weather?lat=${this.latitude}&lon=${this.longitude}&appid=${apiKey}&units=metric&lang=es`
    )
      .then((data) => data.json())
      .then((res) => {
        if (res.cod === 200) {
          this.weather = res;
          this.errorMessage = null;
        } else {
          this.errorMessage = `Error al obtener el clima: ${res.message}`;
          this.weather = null;
        }
      })
      .catch((error) => {
        this.errorMessage = `Error al obtener el clima: ${error.message}`;
      });
  }

  // Maneja el cambio de ubicación
  changeUrl(event) {
    this.location = event.target.value;
  }

  sendContentToTranslate() {
    const event = new CustomEvent("language-selected", {
      detail: {
        language: this.shadowRoot.getElementById("language").value,
        content: encodeURI(this.shadowRoot.innerHTML),
      },
      bubbles: true,
      composed: true,
    });
    this.dispatchEvent(event);
  }
  

  connectedCallback() {
    super.connectedCallback();
    window.addEventListener("text-translated", (e) => this.handleTranslation(e)); // Escuchar traducción
  }

  disconnectedCallback() {
    window.removeEventListener("text-translated", this.handleTranslation); // Remover el listener al desconectarse
    super.disconnectedCallback();
  }

  handleTranslation(event) {
    const translatedContent = event.detail.content;
  
    const parser = new DOMParser();
    const translatedDocument = parser.parseFromString(translatedContent, "text/html");
  
    const idsToTranslate = [
      { id: "input-placeholder", type: "placeholder" },
      { id: "language-label-content", type: "content" },
      { id: "language-option-1", type: "content" },
      { id: "language-option-2", type: "content" },
      { id: "language-option-3", type: "content" },
      { id: "language-option-4", type: "content" },
      { id: "description-content", type:"content"},
      { id: "getweather-button", type: "content" },
      { id: "translate-button", type: "content" },
      { id: "details-1", type: "content" },
      { id: "details-2", type: "content" },
      { id: "details-3", type: "content" },
    ];
  
    idsToTranslate.forEach(({ id, type }) => {
      const elementToTranslate = this.shadowRoot.getElementById(id);
      const translatedElement = translatedDocument.getElementById(id);
  
      if (elementToTranslate && translatedElement) {
        if (type === "placeholder") {
          elementToTranslate.placeholder = translatedElement.getAttribute("placeholder");
        } else if (type === "content") {
          elementToTranslate.innerHTML = translatedElement.innerHTML;       
        }
      }
    });
  }
  getDynamicHtml() {
    return html`
      <div>
        <input id="input-placeholder"
          @input=${this.changeUrl}
          placeholder="Introduce una ubicación"
        />
        <div>
          <label id="language-label-content" for="language">Selecciona un idioma:</label>
          <select id="language">
            <option id="language-option-1" value="en">Inglés</option>
            <option id="language-option-2" value="es">Español</option>≤
            <option id="language-option-3" value="fr">Francés</option>
            <option id="language-option-4" value="it">Italiano</option>
          </select>
        </div>
        <button id="getweather-button" @click=${this.getWeatherMap} class = "getweather">Buscar el clima</button>
        <button id="translate-button" @click=${this.sendContentToTranslate}>
          Traducir página
        </button>

        <div class="weather-container">
          ${this.weather
            ? html`
                <img
                  class="weather-icon"
                  src="https://openweathermap.org/img/wn/${this.weather.weather[0].icon}@2x.png"
                  alt="Icono del clima"
                />
                <div id="description-content" class="description">
                  ${this.weather.weather[0].description}
                </div>
                <div class="temperature">${this.weather.main.temp}°C</div>
                <div class="details">
                  <p id="details-1">Sensación térmica: ${this.weather.main.feels_like}°C</p>
                  <p id="details-2">Humedad: ${this.weather.main.humidity}%</p>
                  <p id="details-3">Viento: ${this.weather.wind.speed} m/s</p>
                </div>
              `
            : this.errorMessage
            ? html`<p style="color: red;">${this.errorMessage}</p>`
            : html`<p>Introduce una ubicación para ver el clima.</p>`}
        </div>
      </div>
    `;
  }

  render() {
    return html`
      ${this.getDynamicHtml()}
    `;
  }
}

customElements.define("get-weather", GetWeather);

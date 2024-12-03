import { LitElement, html, css } from "lit";
import { apiKey } from '../utils/config.js';
import { unsafeHTML } from "lit/directives/unsafe-html.js"; 

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
    const translatedHtml = decodeURIComponent(event.detail.content);
  
    // Parsear el HTML traducido
    const parser = new DOMParser();
    const translatedDom = parser.parseFromString(translatedHtml, "text/html");
  
    // Actualizar los textos del clima
    const descriptionText = translatedDom.querySelector(".description")?.textContent;
    const temperatureText = translatedDom.querySelector(".temperature")?.textContent;
    const detailsText = translatedDom.querySelector(".details")?.innerHTML;
  
    if (this.weather) {
      const description = this.shadowRoot.querySelector(".description");
      if (description) description.textContent = descriptionText;
  
      const temperature = this.shadowRoot.querySelector(".temperature");
      if (temperature) temperature.textContent = temperatureText;
  
      const details = this.shadowRoot.querySelector(".details");
      if (details) details.innerHTML = detailsText;
    }
  
    // Actualizar textos de botones y etiquetas
    const inputPlaceholder = translatedDom.querySelector("input").placeholder;
    const weatherButtonText = translatedDom.querySelector(".getweather")?.textContent;
    const translateButtonText = translatedDom.querySelector(".translate")?.textContent;
    const languageLabelText = translatedDom.querySelector("label[for='language']")?.textContent;
  
    const inputField = this.shadowRoot.querySelector("input");
    if (inputField) inputField.placeholder = inputPlaceholder;
  
    const weatherButton = this.shadowRoot.querySelector(".getweather");
    if (weatherButton) weatherButton.textContent = weatherButtonText;
  
    const translateButton = this.shadowRoot.querySelector(".translate");
    if (translateButton) translateButton.textContent = translateButtonText;
  
    const label = this.shadowRoot.querySelector("label[for='language']");
    if (label) label.textContent = languageLabelText;
  }

  getDynamicHtml() {
    return html`
      <div>
        <input
          @input=${this.changeUrl}
          placeholder="Introduce una ubicación"
        />
        <div>
          <label for="language">Selecciona un idioma:</label>
          <select id="language">
            <option value="en">Inglés</option>
            <option value="es">Español</option>
            <option value="fr">Francés</option>
            <option value="it">Italiano</option>
          </select>
        </div>
        <button @click=${this.getWeatherMap} class = getweather>Buscar el clima</button>
        <button @click=${this.sendContentToTranslate} class = translate>
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
                <div class="description">
                  ${this.weather.weather[0].description}
                </div>
                <div class="temperature">${this.weather.main.temp}°C</div>
                <div class="details">
                  <p>Sensación térmica: ${this.weather.main.feels_like}°C</p>
                  <p>Humedad: ${this.weather.main.humidity}%</p>
                  <p>Viento: ${this.weather.wind.speed} m/s</p>
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
      ${this.webContent
        ? html`${unsafeHTML(this.webContent)}`
        : this.getDynamicHtml()}
    `;
  }
}

customElements.define("get-weather", GetWeather);
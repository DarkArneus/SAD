import { LitElement, html, css } from "lit";

export class GetLocation extends LitElement {
  static styles = css`
    /* Estilos si los necesitas */
  `;

  static properties = {
    latitude: { type: Number },
    longitude: { type: Number },
    errorMessage: { type: String }
  };

  constructor() {
    super();
    this.latitude = null;
    this.longitude = null;
    this.errorMessage = null;

    // Suscribirse al store global para reaccionar a cambios
    store.subscribe(() => this.actualizarUbicacion()); //arrow function para mantenernos en nuestro contexto this () y
  }

  actualizarUbicacion() {
    // Actualiza las propiedades del componente desde el store global
    this.latitude = store.latitud;
    this.longitude = store.longitud;

    // Actualiza el componente
    this.requestUpdate();
  }

  // Método para obtener la ubicación
  async getLocation() {
    if (navigator.geolocation) {
      try {
        const position = await new Promise((resolve, reject) => {
          navigator.geolocation.getCurrentPosition(resolve, reject);
        });
        // Actualizar el store global
        store.setUbicacion(position.coords.latitude, position.coords.longitude);
      } catch (error) {
        this.errorMessage = `Error al obtener la ubicación: ${error.message}`;
        console.error(this.errorMessage);
      }
    } else {
      this.errorMessage = "Geolocalización no soportada.";
      console.error(this.errorMessage);
    }
  }

  // Se llama automáticamente cuando el componente se inserta en el DOM
  firstUpdated() {
    store.subscribe(() => this.actualizarUbicacion());
    this.getLocation(); // Obtener la ubicación del usuario al cargar
  }

  render() {
    return html`
      <div>
        ${this.latitude && this.longitude
          ? html`
              <p>Latitude: ${this.latitude}</p>
              <p>Longitude: ${this.longitude}</p>
            `
          : html`<p>Cargando ubicación...</p>`}

        ${this.errorMessage
          ? html`<p style="color: red;">${this.errorMessage}</p>`
          : ""}
      </div>
    `;
  }
}

customElements.define("get-location", GetLocation);

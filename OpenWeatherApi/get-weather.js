import { LitElement, html } from "lit";
import { apiKey } from './config.js';

export class GetWeather extends LitElement {
  static properties = {
    latitude: { type: Number },
    longitude: { type: Number },
    location: { type: String},
    url_oa: {type: String},
    url_gc: {type: String},
  };

  constructor() {
    super();
    this.latitude = 41.3828939;
    this.longitude = 2.1774322;
    this.location = "Spain"
    this.url_oa = `https://api.openweathermap.org/data/2.5/weather?lat=${this.latitude}&lon=${this.longitude}&appid=${apiKey}`;
    this.url_gc = `http://api.openweathermap.org/geo/1.0/direct?q=${this.location}&limit=5&appid=${apiKey}`;
  }

  getWeatherMap(){
    fetch(this.url_gc)
    .then(data => {return data.json()})
    .then(res =>{
      console.log(res);
      if(res.length > 0){
        this.latitude = res[0].lat;
        this.longitude = res[0].lon;
      } else {
        console.error("No results found for the location.");
      }
      this.url_oa = `https://api.openweathermap.org/data/2.5/weather?lat=${this.latitude}&lon=${this.longitude}&appid=${apiKey}`;
      
    })
    .then(() => this.getWeather())
    
  }

  getWeather(){
    fetch(this.url_oa)
    .then(data => {return data.json()})
    .then(res => {
      console.log(this.url_oa)
      console.log(res)
    })
  }
  
  changeUrl(event){
    const location = event.target;
    const f_location = location.value;
    this.url_gc =  `http://api.openweathermap.org/geo/1.0/direct?q=${f_location}&limit=5&appid=${apiKey}`
  }
  render() {
    return html`
      <div> 
        <input @input=${this.changeUrl} placeholder="Enter location"></input>
        <button @click=${this.getWeatherMap}>Busca el tiempo</button>    
        <p>${this.latitude}</p>
        <p>${this.longitude}</p>        
      </div>
    `;
  }
}

customElements.define("get-weather", GetWeather);

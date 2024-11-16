import { LitElement } from "lit";


function getLocation() {
    return new Promise((resolve, reject) => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    resolve({
                        latitude: position.coords.latitude,
                        longitude: position.coords.longitude,
                    });
                },
                (error) => reject(error)
            );
        } else {
            reject("Geolocation is not supported by this browser.");
        }
    });
}

// Ejemplo de uso
window.onload = async () => {
    try {
        const { latitude, longitude } = await getLocation();
        console.log(`Latitude: ${latitude}, Longitude: ${longitude}`);
    } catch (error) {
        console.error("Error obteniendo la ubicación:", error);
    }
};


var api_url = "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}"




class GetWeather extends LitElement {

}

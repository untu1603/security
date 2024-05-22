import axios from "axios";


export default axios.create({
    baseURL: import.meta.env.VITE_BASE_DOMAIN,
})
export function callApiHeaders(headers) {
    return axios.create({
        baseURL: import.meta.env.VITE_BASE_DOMAIN,
        headers: localStorage.getItem()
    })
}

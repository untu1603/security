import axios from "axios";
import cookies from "js-cookie"

export default axios.create({
    baseURL: import.meta.env.VITE_BASE_DOMAIN,
    headers: cookies.get("accessToken")
})
export function callApiHeaders(headers) {
    return axios.create({
        baseURL: import.meta.env.VITE_BASE_DOMAIN,
        headers: headers
    })
}

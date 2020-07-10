import axios from 'axios'

const axiosInstance = axios.create({
    baseURL: `${process.env.VUE_APP_SERVER_ADDRESS}/api`
})

var interceptor

const addAuthTokenInterceptor = (username, password) => {
    
        interceptor = axiosInstance.interceptors.request.use((response) => {
            response.auth = {
                username, password
            }
            return response
        })
}

const removeAuthTokenInterceptor = () => {
    axiosInstance.interceptors.request.eject(interceptor)
}

export  { addAuthTokenInterceptor, removeAuthTokenInterceptor }
export default axiosInstance
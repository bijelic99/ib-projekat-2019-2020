import axios from 'axios'

const axiosInstance = axios.create({
    baseURL: `${process.env.VUE_APP_SERVER_ADDRESS}/api`
})

export default axiosInstance
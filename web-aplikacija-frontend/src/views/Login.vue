<template>
    <v-container>
        <v-text-field v-model="username" placeholder="Username"/>
        <v-text-field v-model="password" placeholder="Password" type="password"/>
        <v-btn color="primary" v-on:click="login">login</v-btn>
    </v-container>
</template>

<script>
import axios from '../axios'
export default {
    name: 'Login',
    data: function(){
        return {
            username: '',
            password: '',
            user: null
        }
    },
    methods: {
        login: function() {
            axios.post('/users/login', { email: this.username, password: this.password}).then(({data})=>{
                this.user = data
                alert("Uspesan login")
                this.$router.push({name:"Home", params:{
                    user: this.user
                }})
            })
            .catch((err)=>{
                console.log(err)
                alert("Login failed")
            })
        }
    }

}
</script>

<style>

</style>
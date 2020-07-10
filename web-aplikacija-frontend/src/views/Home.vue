<template>
  <v-container v-if="user">
    <v-row justify="space-between">
      <v-col>
        {{user.authority.authority}} - {{user.email}}
        <a :href="userJksLink">Moj .jks fajl</a>
      </v-col>
      <v-col>
        <v-btn @click="logout">Logout</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-text-field v-model="searchString" placeholder="Search for people" />
      <v-btn @click="searchPeople">Search</v-btn>
    </v-row>
    <v-row v-for="user in people" :key="user.email" justify="space-between">
      {{user.email}}
      <a :href="userCertLink(user)">korisnikov certificate</a>
    </v-row>
    <span v-if="user.authority.authority === 'Admin'">
      <v-row><span></span>Useri koje treba approve <v-btn @click="getNotApproved()">Load</v-btn></v-row>
      <v-col>
        <v-row v-for="user in needsApproval" :key="user.email" justify="space-between">
          {{user.email}}
          <v-btn @click="approve(user.id)">Odobri</v-btn>
        </v-row>
      </v-col>
    </span>
  </v-container>
</template>

<script>
import axios from "../axios";
export default {
  name: "Home",
  components: {},
  data: function() {
    return {
      searchString: "",
      people: [],
      needsApproval: []
    };
  },
  computed: {
    user: function() {
      return this.$route.params.user;
    },
    userJksLink: function() {
      return `${process.env.VUE_APP_SERVER_ADDRESS}/api/users/${this.user.id}/get-jks`;
    }
  },
  methods: {
    searchPeople: function() {
      axios
        .get("/users", {
          params: {
            email: this.searchString
          }
        })
        .then(({ data }) => {
          this.people = data;
        })
        .catch(err => console.log(err));
    },
    logout() {
      //Obrisati interceptor iz axiosa
      this.$router.push("/login");
    },
    userCertLink(user) {
      return `${process.env.VUE_APP_SERVER_ADDRESS}/api/users/${user.id}/get-certificate`;
    },
    approve: function(userId) {
        if (this.user) {
          axios
            .put(
              `/users/admins/${this.user.id}/approve/${userId}`
            )
            .then(() => {
              this.needsApproval = this.needsApproval.filter(
                u => u.id !== userId
              );
              alert("Uspesno odobren");
            })
            .catch(err => {
              alert("Nije odobren, greska");
              console.log(err);
            });
        
      }
    },
    getNotApproved(){
      axios.get(`/users/not-approved`).then(({data})=>{
      this.needsApproval = data
      })
    }
  },
  mounted() {
    
    
  }
};
</script>

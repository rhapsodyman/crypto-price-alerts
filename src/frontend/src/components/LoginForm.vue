<template>
   <div class="row">
      <div class="col-md-4 offset-md-4">
         <div class="bg-light mt-4 p-4">
            <form @submit.prevent="submit" class="row g-3">
               <h4>Welcome Back to Crypto Alerts</h4>
               <div class="col-12">
                  <label>Username</label>
                  <input v-model="form.username" type="text" name="username" class="form-control" placeholder="Username" required>
               </div>
               <div class="col-12">
                  <label>Password</label>
                  <input v-model="form.password" type="password" name="password" class="form-control" placeholder="Password" required>
               </div>
               <div class="col-12 mt-4">
                  <button type="submit" class="btn btn-dark float-end" :disabled="!allAllFieldsFilledIn">Login</button>
               </div>
            </form>
            <hr class="mt-4">
            <div class="col-12">
               <p class="text-center mb-0">
                  Have not account yet?  
                  <router-link to="/register">Register</router-link>
               </p>
            </div>
            <div v-if="errorMessage" class="alert alert-danger" role="alert">
               {{errorMessage}}
            </div>
         </div>
      </div>
   </div>
</template>

<script>
export default {
    name: 'LoginForm',

    props: [],

    data: function() {
        return {
            form: {
                username: "",
                password: ""
            },
            errorMessage: '',
            successful: false
        }
    },

    computed: {
        allAllFieldsFilledIn: function() {
            return this.form.username.length >= 3 && this.form.password.length >= 6
        }
    },

    methods: {
        submit() {
            this.$store.dispatch('login', this.form).then(
                () => {
                    this.successful = true;
                    this.$router.push("/alerts");
                },
                error => {
                    this.errorMessage = error.response.data.message || error.toString()
                    this.successful = false;
                }
            );
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

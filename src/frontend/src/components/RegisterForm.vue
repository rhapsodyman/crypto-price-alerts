<template>
   <div class="row">
      <div class="col-md-4 offset-md-4">
         <div class="bg-light mt-4 p-4">
            <form @submit.prevent="submit" class="row g-3">
               <h4>Register</h4>
               <div class="col-12">
                  <label>Username</label>
                  <input v-model="form.username" type="text" name="username" class="form-control" required>
               </div>
               <div class="col-12">
                  <label>Password</label>
                  <input v-model="form.password" type="password" name="password" class="form-control" minlength="6" required>
               </div>
               <div class="col-12">
                  <label>Phone Number</label>
                  <input v-model="form.phone" type="text" name="phone" class="form-control" placeholder="+37367720312" required>
               </div>
               <div class="col-12 mt-4">
                  <button type="submit" class="btn btn-dark float-end" :disabled="!allAllFieldsFilledIn">Register</button>
               </div>
               <div v-if="errorMessage" class="alert alert-danger" role="alert">
                  {{errorMessage}}
               </div>
            </form>
         </div>
      </div>
   </div>
</template>

<script>

export default {
    name: 'RegisterForm',

    props: [],

    data: function() {
        return {
            form: {
                username: "",
                password: "",
                phone: ""
            },
            errorMessage: '',
            successful: false
        }
    },

    computed: {
        allAllFieldsFilledIn: function() {
            return this.form.username.length >= 3 && this.form.password.length >= 6 && this.form.phone.length > 0
        }
    },

    methods: {
        submit() {
            this.$store.dispatch('register', this.form).then(
                () => {
                    this.successful = true;
                    this.$router.push("/register-success");
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

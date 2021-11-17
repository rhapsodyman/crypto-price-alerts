<template>
   <div class="row">
      <div class="col-md-4 offset-md-4">
         <div class="bg-light mt-4 p-4">
            <div class="alert alert-success" role="alert">
               Registered Succesfully!
            </div>
            <div class="col-12 mt-4">
               <button type="submit" class="btn btn-dark float-end" @click="login">Login</button>
            </div>
         </div>
      </div>
   </div>
</template>


<script>

export default {
    name: 'RegisterSuccess',

    props: [],

    data: function() {
        return {
            form: {
                username: "",
                password: "",
                phone: ""
            }
        }
    },

    methods: {
        login() {
            this.$router.push("/login");
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

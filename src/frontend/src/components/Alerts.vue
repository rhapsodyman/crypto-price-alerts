<template>
   <div id="app">
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
         <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
         <span class="navbar-toggler-icon"></span>
         </button>
         <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
               <a class="nav-item nav-link active" href="#">Alerts<span class="sr-only">(current)</span></a>
               <span id="logout-btn" class="nav-item nav-link" @click="logout">Logout</span>
            </div>
         </div>
      </nav>
      <div class="container">
         <h1>Add new alert</h1>
         <form>
            <div class="form-group">
               <label for="Name">Crypto Symbol:</label>
               <div class="input-group">
                  <input type="text" v-model="enterNewAlert.symbol" class="form-control" aria-describedby="nameHelp" placeholder="ex BTC, ETH">
                  <button class="btn btn-outline-secondary" type="button" @click="checkSymbol" :disabled="this.enterNewAlert.symbol.length == 0" >Check if valid</button>
               </div>
               <div v-if="symbolIsValid" class="alert alert-success" role="alert"> Is a valid symbol</div>
               <div v-if="symbolIsInvalid" class="alert alert-warning" role="alert">Is not a valid symbol</div>
            </div>
            <div class="form-group">
               <label for="type">Select operator:</label>
               <select v-model="enterNewAlert.operator" class="custom-select">
                  <option v-for="type in types" :key="type" :value="type">{{type}}</option>
               </select>
            </div>
            <div class="form-group">
               <label for="Name">Crypto Value:</label>
               <input type="number" v-model="enterNewAlert.targetValue" class="form-control" aria-describedby="nameHelp" placeholder="Enter Crypto Value">
            </div>
            <button type="button" class="btn btn-primary" @click="addNewAlert" :disabled="!allAllFieldsFilledIn" >Add</button>  
         </form>
         <h1 class="mt-5"> Your current alerts</h1>
         <div class="card shadow-lg">
            <div class="card-body">
               <table class="table">
                  <thead class="thead-dark">
                     <tr>
                        <th scope="col">Symbol</th>
                        <th scope="col">Operator</th>
                        <th scope="col">Target Value</th>
                        <th scope="col">Triggered Value</th>
                        <th scope="col">Triggered</th>
                        <th scope="col">Remove</th>
                     </tr>
                  </thead>
                  <tbody>
                     <alert-description-row v-for="row in this.alerts"  v-on:remove-alert="removeAlert($event)"
                        :alertId="row.id"
                        :symbol="row.symbol"
                        :operator="row.operator"
                        :targetValue="row.targetValue"
                        :triggeredValue="row.triggeredValue"
                        :triggered="row.wasTriggered"
                        :key="row.symbol"
                        ></alert-description-row>
                  </tbody>
               </table>
            </div>
         </div>
      </div>
   </div>
</template>

<script>
import AlertDescriptionRow from './AlertDescriptionRow.vue'

import http from "../http-common";
import authHeader from '../services/auth-header';


export default {
    name: 'app',
    components: {
        AlertDescriptionRow
    },

    data: function() {
        return {
            types: ["GREATER_THAN", "LESS_THAN"],
            symbolIsValid: false,
            symbolIsInvalid: false,

            enterNewAlert: {
                symbol: '',
                operator: '',
                targetValue: ''
            },
        }
    },

    computed: {
        allAllFieldsFilledIn: function() {
            return this.symbolIsValid && this.enterNewAlert.symbol.length > 0 && this.enterNewAlert.operator.length > 0 && this.enterNewAlert.targetValue.length > 0
        },

        alerts() {
            return this.$store.getters.StateAlerts
        }
    },

    mounted() {
        this.$store.dispatch('GetAlerts');
    },

    methods: {
        logout: function() {
            this.$store.dispatch('logout');
            this.$router.push('/login');
        },

        clearForm: function() {
            this.enterNewAlert = {
                symbol: '',
                operator: '',
                value: ''
            }
            this.symbolIsValid = false
            this.symbolIsInvalid = false
        },

        addNewAlert: function() {
            this.$store.dispatch('CreateAlert', this.enterNewAlert)
            this.clearForm()
        },

        removeAlert: function(id) {
            this.$store.dispatch('DeleteAlert', id)
        },


        checkSymbol: async function() {
            const res = await http.get('/priceAlerts/crypto/isValid', { headers: authHeader() , params: { symbol: this.enterNewAlert.symbol}})
            this.symbolIsValid = (res.data == "Valid") 
            this.symbolIsInvalid = !(res.data == "Valid")
        },
    }
}
</script>

<style>
 #app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  
  margin-top: 60px;
}

#logout-btn {
  cursor : pointer
}

</style>

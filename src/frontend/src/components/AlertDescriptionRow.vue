<template>
    <tr>
      <td>{{symbol}}</td>
      <td>{{operator}}</td>
      <td>{{targetValue}}</td>
      <td>{{triggeredValue}}</td>
      <td>
        <button v-if="triggered" type="button" class="btn btn-outline-success" disabled>Yes</button>
        <button v-if="!triggered" type="button" class="btn btn-outline-info" disabled>Waiting</button>     
      </td>    
      <td><button type="button" class="btn btn-sm btn-danger"  
              v-if="triggered == false"    
              @click="$emit('remove-alert', alertId)">X
            </button>
      </td>
    </tr> 
</template>

<script>
export default {
  name: 'AlertDescriptionRow',

  props: ["alertId", "symbol", "operator", "targetValue", "triggeredValue" , "triggered"],

  data: function() {
    return {
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>

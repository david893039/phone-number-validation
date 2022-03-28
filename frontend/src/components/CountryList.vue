<template>
  <div>
    <table class="table responsive" id="sort">
      <thead>
        <tr>
        <th scope="col">Country</th>
        <th scope="col">State</th>
        <th scope="col">Country Code</th>
        <th scope="col">Number</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>
            <input class="form-control" placeholder="Filter by country" type="text" v-on:keyup="filterByCountry"> 
          </td>
          <td>
            <select class="form-control" v-on:change="filterByState($event)">
              <option selected value="">Filter by state</option>
              <option value="valid">Valid</option>
              <option value="not valid">Not Valid</option>
            </select>
          </td>
        </tr>
        <tr v-for="result in results" v-bind:key="result">
          <td data-table-header="Country">{{result.country}}</td>
          <td data-table-header="State">{{result.state}}</td>
          <td data-table-header="Country Code">{{result.countryCode}}</td>
          <td data-table-header="Number">{{result.phone}}</td>
        </tr>
        </tbody>
    </table>
    <paginate
        :page-count="paginationDetails.totalPages"
        :page-range="paginationDetails.pageRange"
        :margin-pages="2"
        :click-handler="clickCallback"
        :prev-text="'Prev'"
        :next-text="'Next'"
        :container-class="'pagination'"
        :page-class="'page-item'"
      >
      </paginate>
  </div>
</template>

<script>
import Paginate from 'vuejs-paginate-next';
export default{
  components: {
      paginate: Paginate
    },
  created(){
    this.fetchData(this.pageNum,this.country,this.state);
  },
  data(){
    return{
      paginationDetails: '',
      results: [],
      country: '',
      state: '',
      pageNum: 0
    }
  },
  methods:{
    filterByCountry(event){
        var country = event.target.value;
        this.country = country;
        this.fetchData(this.pageNum,country,this.state)
    },
    filterByState(event){
        var state = event.target.value;
        this.state = state;
        this.fetchData(this.pageNum,this.country,state)
    },
    clickCallback (pageNum){
        this.pageNum = pageNum;
        this.fetchData(this.pageNum-1,this.country,this.state)
      },
    fetchData(pageNum, country, state){
        var url = "http://localhost:8081/v1/customers?";
        console.log(pageNum);
        
        if(pageNum != ''){
          url = url+"page="+pageNum;
        }
        if(country != ''){
          url = url+"&country="+country;
        }
        if(state != ''){
          url = url+"&state="+state;
        }
        console.log(url);

        fetch(url)
        .then(response => {
          if(response.ok){
            return response.json();
          }
        })
        .then(data => {
          var content = data.body.content.slice();
          const resultss = [];
          var paginationDetails = {
            totalPages: data.body.totalPages,
            pageRange: data.body.size
          }
          this.paginationDetails = paginationDetails;
          for(var id in content){
            resultss.push({
              id: content[id].id,
              country: content[id].country,
              phone: content[id].phone,
              state: content[id].state,
              countryCode: content[id].countryCode
            });
          }
          this.results = resultss;
        });
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
 
tr:nth-of-type(odd) {
  background: #f4f4f4;
}
 
tr:nth-of-type(even) {
  background: #fff;
}
 
th {
  background: #2d76ff;
  color: #ffffff;
  font-weight: 300;
}
 
td,
th {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}
 
td:nth-of-type(1) {
  font-weight: 500 !important;
}
 
td {
  font-family: 'Roboto', sans-serif !important;
  font-weight: 300;
  line-height: 20px;
}
 
span {
  font-style: italic
}
 
@media only screen and (max-width: 760px),
(min-device-width: 768px) and (max-device-width: 1024px) {
 
  table.responsive,
  .responsive thead,
  .responsive tbody,
  .responsive th,
  .responsive td,
  .responsive tr {
    display: block !important;
  }
 
  .responsive thead tr {
    position: absolute !important;
    top: -9999px;
    left: -9999px;
  }
 
  .responsive tr {
    border: 1px solid #ccc;
  }
 
  .responsive td {
    border: none;
    border-bottom: 1px solid #eee !important;
    position: relative !important;
    padding-left: 25% !important;
  }
 
  .responsive td:before {
    position: absolute !important;
    top: 6px;
    left: 6px;
    width: 45%;
    padding-right: 10px;
    white-space: nowrap !important;
    font-weight: 500 !important;
  }
 
 
  .responsive td:before {
    content: attr(data-table-header) !important;
  }
  }
</style>

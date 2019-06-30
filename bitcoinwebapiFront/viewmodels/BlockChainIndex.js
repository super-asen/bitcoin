
var app = new Vue({
    el: '#app',
    data: {
        blocks:[],
        txs:[],
        search:'',
          loading:true,
          blockchainId:'1',
          rate:'',
          socket: '',
          stompclient: ''
    },
   
    mounted() {
        this.getRate();
        this.getBlock();
        // this.getTx();
        this.getMovementTxIndex();
        this.$watch('blocks',function(val){  
            this.$nextTick(function() {  
               this.loading=false;
            });  
        })  
        this.$watch('txs',function(val){  
            this.$nextTick(function() {  
               this.loading=false;
            });  
        }) 
        
       
    },
    methods: {
        getBlock() {
            axios.get('/block/blockIndex/',{

            })
                .then(function (response) {
                    app.blocks=response.data
                })
        },
        getTx() {
            axios.get('/transaction/txIndex', {

            })
                .then(function (response) {
                    app.txs=response.data
                })
        },
        autoAddress(){
            this.search="2NBMEXusiFvQKHhu1ejNgp23egZhkDmdccW"
        },
        autoTransaction(){
            this.search="2a269bc1e711e4b8206edcf74916f5ddd02dd0d0b6f236a9f49cd5d8d619683d"
        },
        autoHeight(){
            this.search="1566499"
        },
        moreBlock(){
            location.href="BlockViewMore.html"
        },
        moreTx(){
            location.href="TransationViewMore.html"
        },
        submit(){
            if(this.search.replace(/(^\s*)|(\s*$)/g, "")!=''){
                axios.get('/blockChain/search',{
                    params: {
                        param: this.search
                      }
                })
                .then(function (response) {
                    var result = response.data
                    if(result==''){
                        alert("我们找不到你要找的东西")
                        return ;
                    }else{
                        location.href=result
                    }
                })
            }else{
                alert("请输入字段")
            }
        },
        currentHeight(index,row){
            location.href="BlockByHeight.html?height="+row.height
        },
        currentTxHash(index,row){
            location.href="TransationByHash.html?txhash="+row.txhash
        },
        currentGetBlock(tab){
            if(tab.index==0){
                this.getBlock();
            }
        },
        getRate(){
            axios.get('/transaction/getRate')
            .then(function (response) {
                app.rate=response.data;
            })
        },
        getMovementTxIndex(){
            this.socket = new SockJS('http://localhost:8080/movement');
            this.stompclient = Stomp.over(this.socket);
            this.stompclient.connect({}, frame => {
                this.stompclient.subscribe('/topic/txIndex', function (data) {
                    var txsJson=JSON.parse(data.body);
                    app.txs=txsJson;
                });
            });
        }
    }
})

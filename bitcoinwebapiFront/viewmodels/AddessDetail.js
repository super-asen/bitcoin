var app = new Vue({
    el: '#app',
    data: {
        addresses:'',
        loading:true,
        list: [

        ]
    },
    mounted() {
        var url = new URL(location.href);
        var address = url.searchParams.get("address");
        this.getAddresses(address);
        this.$watch('addresses', function (val) {
            this.$nextTick(function () {
                this.loading = false;
            });
        })
    },
    methods: {
        getAddresses(address){
            axios.get('/transaction/getAddress/', {
                params: {
                    address:address
                }
            })
            .then(function (response) {
                var addresse = response.data
                app.addresses = addresse;
                app.list = addresse.txs;
            })
        },
        currentTxHash(txhash){
            location.href="TransationByHash.html?txhash="+txhash
        },
        currentAddress(address) {
            location.href = "AddressDetail.html?address=" + address
        }
    }
        
})
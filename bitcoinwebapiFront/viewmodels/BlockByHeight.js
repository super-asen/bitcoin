var app = new Vue({
    el: '#app',
    data: {
        blocks: '',
        loading: true,
        //list: []
    },
    mounted() {
        var url = new URL(location.href);
        var height = url.searchParams.get("height");
        this.getblock(height);
        this.$watch('blocks', function (val) {
            this.$nextTick(function () {
                this.loading = false;
            });
        })
    },
    methods: {
        getblock(height) {
            axios.get('/block/getBlockByHeigh', {
                params: {
                    height:height
                }
            })
                .then(function (response) {
                    app.blocks = response.data;
                   
                })
        },

        currentHeight(height) {
            location.href = "BlockByHeight.html?height=" + height
        },
    }
})
let app = new Vue({
    el: '#app',
    data: {
        items: [],
        random: [],
        user: []
    },

    computed: {
        itemCount() {
            let n = 0;
            for (let i of this.items) n += i.amount * 1;
            return n;
        },

        priceCount() {
            let n = 0.0;
            for (let i of this.items) n += (i.sellPrice * i.amount);
            return n;
        }
    },

    mounted: function() {
        axios.get('/api/random/item').then(function (response) {
            if (response.data.success === false) return;
            app.random = response.data;
        });

        // init cart
        if (sessionStorage['cart']) {
            let a = [];
            let cart = JSON.parse(sessionStorage.getItem('cart'));
            for (let i of cart) {
                axios.get('/api/item/' + i.id).then(function (response) {
                    if (response.data.success === false) return;
                    let item = response.data;
                    item.amount = i.amount;
                    a.push(item);
                });
                console.log('/api/item/' + i.id);
            }
            this.items = a;
        }

    },

    methods: {
        resetItem($e) {
            let id = $e.target.dataset.id;
            let iter = app.items.findIndex(i => i.id == id);
            app.items.splice(iter, 1);
            this.updateSession()
        },

        resetSession() {
            sessionStorage.clear();
            window.location.href = '/shop';
        },

        updateSession() {
            let cart = [];
            for (let i of app.items) cart.push( {id: i.id, amount: i.amount} )
            sessionStorage.setItem("cart", JSON.stringify(cart));
        }
    }
});
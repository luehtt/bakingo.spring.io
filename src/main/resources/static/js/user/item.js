let app = new Vue({
    el: '#app',
    data: {
        item: [],
        random: [],
        comments: [],
        response: [],
        id: 0,
        amount: 0
    },

    mounted: function () {
        // init data
        this.id = document.querySelector('#item_id').value;
        axios.get('/api/random/item/' + this.id).then(function (response) {
            if (response.data.success === false) app.response = response.data;
            else app.random = response.data;
        });
        axios.get('/api/comment/item/' + this.id).then(function (response) {
            if (response.data.success === false) app.response = response.data;
            else app.comments = response.data;
        });
        axios.get('/api/item/' + this.id).then(function (response) {
            if (response.data.success === false) app.response = response.data;
            else {
                app.item = response.data;
                app.item.amount = 0;
            }
        });

        // init amount session
        if (sessionStorage['cart']) {
            let cart = JSON.parse(sessionStorage.getItem('cart'));
            for (let i of cart) {
                if (i.id !== this.id) continue;
                this.amount = i.amount;
                break;
            }
        }
    },

    methods: {
        clearSearch() {
            this.search = '';
            this.response = [];
        },

        addItem($e) {
            let id = app.id;
            if (app.amount === 0) app.amount++;
            if (sessionStorage['cart']) {
                let cart = JSON.parse(sessionStorage.getItem('cart'));
                let duplicate = 0;
                for (let i of cart) {
                    if (i.id !== id) continue;
                    i.amount = app.amount;
                    duplicate++;
                    break;
                }
                if (duplicate === 0) cart.push({id: id, amount: app.amount});
                sessionStorage.setItem("cart", JSON.stringify(cart));
            } else {
                let cart = [];
                cart.push({id: id, amount: app.amount});
                sessionStorage.setItem("cart", JSON.stringify(cart));
            }
        },

        deleteItem($e) {
            let id = app.id;
            app.amount = 0;
            if (sessionStorage['cart']) {
                let cart = JSON.parse(sessionStorage.getItem('cart'));
                let iter = cart.findIndex(i => i.id === id);
                if (iter !== -1) cart.splice(iter, 1);
                sessionStorage.setItem("cart", JSON.stringify(cart));
            }
        }
    }
});
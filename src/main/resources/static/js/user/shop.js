let app = new Vue({
    el: '#app',
    data: {
        search: '',
        groups: [],
        items: [],
        selected: 0,
        response: []
    },

    computed: {
        filter() {
            let a = [];
            for (let i of this.items) {
                if (i.name.toLowerCase().includes(this.search.toLowerCase()) === false) continue;
                if (this.selected != 0 && i.group.id != this.selected) continue;
                a.push(i);
            }
            a.sort((a, b) => a.name.localeCompare(b.name));
            return a;
        }
    },

    mounted: function () {
        axios.get('/api/item').then(function (response) {
            if (response.data.success === false) app.response = response.data;
            else {
                app.items = response.data;
                if (sessionStorage['cart']) {
                    let cart = JSON.parse(sessionStorage.getItem('cart'));
                    for (let i of app.items) i.amount = 0;
                    for (let i of cart) {
                        let item = app.items.find( x => x.id == i.id );
                        if (item != null) item.amount = i.amount;
                    }
                }
            }
        });
        axios.get('/api/group').then(function (response) {
            if (response.data.success === false) app.response = response.data;
            else app.groups = response.data;
        });
    },

    methods: {
        clearSearch() {
            this.search = '';
            this.response = [];
        },

        // this event add to select bc thymeleaf doesnt agree with select v-model
        selectGroup($e) {
            app.selected = $e.target.value;
        },

        addSession($e) {
            let id = $e.target.dataset.id;
            let amount = 0;
            if (sessionStorage['cart']) {
                let cart = JSON.parse(sessionStorage.getItem('cart'));
                let get = cart.find( x => x.id == id );
                if (get != null) get.amount++;
                else {
                    get = { id: id, amount: 1 };
                    cart.push(get);
                }
                amount = get.amount;
                sessionStorage.setItem("cart", JSON.stringify(cart));
            } else {
                let cart = [];
                cart.push( {id: id, amount: 1} );
                amount = 1;
                sessionStorage.setItem("cart", JSON.stringify(cart));
            }

            // must force update else the amount didnt render for ??? reason
            let item = app.items.find( x => x.id == id );
            item.amount = amount;
            this.$forceUpdate();
        },
    }
});
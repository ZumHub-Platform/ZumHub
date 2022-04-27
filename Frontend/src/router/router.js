//import { Vue } from 'vue';
import {
    createRouter,
    createWebHashHistory
} from 'vue-router';
import Authorization from '../components/Authorization.vue'
import Main from '../components/Main.vue'

let router = new createRouter({
    history: createWebHashHistory(),
    routes: [{
            path: '/',
            name: 'Authorization',
            component: Authorization,
        },
        {
            path: '/main',
            name: 'Main',
            component: Main,
        }
    ]
})


export default router;
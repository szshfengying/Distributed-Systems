import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import OpenAcc from '@/components/OpenAcc'
import Details from '@/components/Details'
import Balance from '@/components/Balance'
import Transfer from '@/components/Transfer'
import Test from '@/components/Test'
import Info from '@/components/Info'
import tt from '@/components/tt'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/tt',
      name: 'tt',
      component: tt
  },
    {
      path: '/Test',
      name: 'Test',
      component: Test
  },
    {
      path: '/',
      name: 'Login',
      component: Login
  },

    {
      path: '/OpenAcc',
      name: 'OpenAcc',
      component: OpenAcc
    },
    {
      path: '/Details',
      name: 'Details',
      components: {
        default: Details,
      }
    },
    {
      path: '/Info',
      name: 'Info',
      component: Info,
    },
    {
      path: '/Transfer',
      name: 'Transfer',
      component: Transfer
    },
    {
      path: '/Balance',
      name: 'Balance',
      component: Balance
    },
  ]
})

import { createRouter, createWebHistory } from "vue-router"
import MateriasPrimasView from "@/views/MateriasPrimasView.vue"
import ProdutosView from "@/views/ProdutosView.vue"
import ProducaoView from "@/views/ProducaoView.vue"

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", redirect: "/materias-primas" },
    { path: "/materias-primas", component: MateriasPrimasView },
    { path: "/produtos", component: ProdutosView },
    { path: "/producao", component: ProducaoView }
  ]
})

export default router
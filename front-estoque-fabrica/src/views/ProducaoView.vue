<script setup>

import {ref, onMounted} from "vue"
import {api} from "../api/http"

const plano = ref(null)

async function otimizar(){

 const response = await api.get("/producao/otimizar")

 plano.value = response.data

}

const produtos = ref([])
const produtoSelecionado = ref("")
const quantidade = ref(1)

const itens = ref([])

async function carregarProdutos(){

 const response = await api.get("/produtos")

 produtos.value = response.data

}

function adicionarProduto(){

 itens.value.push({
  produtoNome: produtoSelecionado.value,
  quantidade: quantidade.value
 })

}

async function enviarPlano(){

 const response = await api.post("/producao/manual",{
  itens: itens.value
 })

 plano.value = response.data

}

onMounted(()=>{
 carregarProdutos()
})

</script>

<template>

<h2 class="text-xl font-bold mb-4">Produção Manual</h2>

<div class="flex gap-3 mb-4">

<select
v-model="produtoSelecionado"
class="border rounded p-2"
>

<option disabled value="">
Selecione produto
</option>

<option
v-for="p in produtos"
:key="p.id"
:value="p.nome"
>

{{p.nome}}

</option>

</select>

<input
type="number"
v-model="quantidade"
class="border rounded p-2 w-24"
/>

<button
@click="adicionarProduto"
class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
>
Adicionar
</button>

</div>

<ul class="mb-4 bg-white shadow rounded p-3">

<li
v-for="item in itens"
:key="item.produtoNome"
class="border-b py-1"
>

{{item.produtoNome}} → {{item.quantidade}}

</li>

</ul>

<button
@click="enviarPlano"
class="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded"
>
Gerar Plano Manual
</button>

<hr class="my-8">

<h2 class="text-xl font-bold mb-4">Plano de Produção Automático</h2>

<button
@click="otimizar"
class="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded mb-4"
>

Gerar Plano

</button>

<div
v-if="plano"
class="bg-white shadow rounded p-4"
>

<h3 class="font-semibold mb-2">Produtos</h3>

<ul>

<li
v-for="(qtd,nome) in plano.plano"
:key="nome"
class="border-b py-1"
>

{{nome}} → {{qtd}}

</li>

</ul>

<h3 class="mt-4 font-bold">

Valor total: R$ {{plano.valorTotal}}

</h3>

</div>

</template>
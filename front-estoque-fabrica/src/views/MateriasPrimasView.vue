<script setup>
import {ref,onMounted} from "vue"
import {api} from "../api/http"

const materias = ref([])

const nome = ref("")
const quantidade = ref(0)

async function carregar(){

 const response = await api.get("/materias-primas")

 materias.value = response.data

}

async function criar(){

 await api.post("/materias-primas",{
   nome:nome.value,
   quantidadeEmEstoque:quantidade.value
 })

 nome.value=""
 quantidade.value=0

 carregar()

}

onMounted(carregar)

</script>

<template>

<h2 class="text-xl font-bold mb-4">Matérias Primas</h2>

<div class="flex gap-3 mb-6">

<input
v-model="nome"
placeholder="Nome"
class="border rounded p-2 w-48"
/>

<input
v-model="quantidade"
type="number"
placeholder="Quantidade"
class="border rounded p-2 w-40"
/>

<button
@click="criar"
class="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded"
>
Adicionar
</button>

</div>

<table class="w-full bg-white shadow rounded">

<thead class="bg-gray-100">

<tr>

<th class="p-3 text-left">Nome</th>
<th class="p-3 text-left">Estoque</th>

</tr>

</thead>

<tbody>

<tr
v-for="m in materias"
:key="m.nome"
class="border-t hover:bg-gray-50"
>

<td class="p-3">{{m.nome}}</td>
<td class="p-3">{{m.quantidadeEmEstoque}}</td>

</tr>

</tbody>

</table>

</template>
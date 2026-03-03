<script setup>

import {ref,onMounted} from "vue"
import {api} from "../api/http"

const produtos = ref([])
const materias = ref([])

const nome = ref("")
const valor = ref(0)

const editando = ref(null)

const mensagem = ref("")
const erro = ref("")

const composicao = ref([
 {
   materiaPrimaNome:"",
   quantidadeNecessaria:0
 }
])

async function carregarProdutos(){
 const response = await api.get("/produtos")
 produtos.value=response.data
}

async function carregarMaterias(){
 const response = await api.get("/materias-primas")
 materias.value=response.data
}

async function criar(){

 try{

 await api.post("/produtos",{
   nome:nome.value,
   valor:valor.value,
   composicao:composicao.value
 })

 mensagem.value="Produto criado com sucesso"
 erro.value=""

 nome.value=""
 valor.value=0

 carregarProdutos()

 }catch(e){

 erro.value="Erro ao criar produto"
 mensagem.value=""

 }

}

async function deletar(nome){

 if(!confirm("Deseja deletar este produto?")) return

 try{

 await api.delete(`/produtos/deletar/${nome}`)

 mensagem.value="Produto deletado"
 erro.value=""

 carregarProdutos()

 }catch(e){

 erro.value="Erro ao deletar produto"
 mensagem.value=""

 }

}

function editar(produto){

 editando.value={
  nome:produto.nome,
  valor:produto.valor
 }

}

async function salvarEdicao(){

 try{

 await api.put("/produtos",editando.value)

 mensagem.value="Produto atualizado"
 erro.value=""

 editando.value=null

 carregarProdutos()

 }catch(e){

 erro.value="Erro ao atualizar"
 mensagem.value=""

 }

}

function adicionarMateria(){

 composicao.value.push({
   materiaPrimaNome:"",
   quantidadeNecessaria:0
 })

}

onMounted(()=>{

 carregarProdutos()
 carregarMaterias()

})

</script>

<template>

<h2 class="text-xl font-bold mb-4">Produtos</h2>

<div v-if="mensagem" class="bg-green-200 p-2 rounded mb-2">
{{mensagem}}
</div>

<div v-if="erro" class="bg-red-200 p-2 rounded mb-2">
{{erro}}
</div>

<div class="flex gap-2 mb-4">

<input v-model="nome" placeholder="Nome produto" class="border p-2 rounded"/>

<input v-model="valor" type="number" placeholder="Valor" class="border p-2 rounded"/>

</div>

<div v-for="item in composicao" class="flex gap-2 mb-2">

<select v-model="item.materiaPrimaNome" class="border p-2 rounded">

<option v-for="m in materias" :value="m.nome">
{{m.nome}}
</option>

</select>

<input
type="number"
v-model="item.quantidadeNecessaria"
placeholder="Qtd necessária"
class="border p-2 rounded w-32"
/>

</div>

<button
@click="adicionarMateria"
class="bg-gray-500 text-white px-3 py-1 rounded mr-2"
>
Adicionar matéria
</button>

<button
@click="criar"
class="bg-green-500 text-white px-3 py-1 rounded"
>
Criar Produto
</button>

<hr class="my-6">

<table class="min-w-full border">

<thead class="bg-gray-100">

<tr>

<th class="p-2">Produto</th>
<th class="p-2">Valor</th>
<th class="p-2">Ações</th>

</tr>

</thead>

<tbody>

<tr
v-for="p in produtos"
:key="p.nome"
class="border-t"
>

<td class="p-2">

<span v-if="editando?.nome !== p.nome">
{{p.nome}}
</span>

<input
v-else
v-model="editando.nome"
class="border p-1 rounded"
/>

</td>

<td class="p-2">

<span v-if="editando?.nome !== p.nome">
R$ {{p.valor}}
</span>

<input
v-else
type="number"
v-model="editando.valor"
class="border p-1 rounded"
/>

</td>

<td class="p-2 flex gap-2">

<button
v-if="editando?.nome !== p.nome"
@click="editar(p)"
class="bg-blue-500 text-white px-3 py-1 rounded"
>
Editar
</button>

<button
v-if="editando?.nome !== p.nome"
@click="deletar(p.nome)"
class="bg-red-500 text-white px-3 py-1 rounded"
>
Deletar
</button>

<button
v-if="editando?.nome === p.nome"
@click="salvarEdicao"
class="bg-green-500 text-white px-3 py-1 rounded"
>
Salvar
</button>

<button
v-if="editando?.nome === p.nome"
@click="editando=null"
class="bg-gray-500 text-white px-3 py-1 rounded"
>
Cancelar
</button>

</td>

</tr>

</tbody>

</table>

</template>
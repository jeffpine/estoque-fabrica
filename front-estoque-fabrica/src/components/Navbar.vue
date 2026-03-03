<template>
  <nav class="navbar">

    <h1>🏭 Sistema de Produção</h1>

    <div class="links">
      <router-link to="/">Estoque</router-link>
      <router-link to="/produtos">Produtos</router-link>
      <router-link to="/producao">Produção</router-link>
    </div>

  </nav>
</template>

<style scoped>

.navbar{
  display:flex;
  justify-content:space-between;
  align-items:center;

  padding:16px;

  background:#1e293b;
  color:white;
}

.links a{
  margin-left:20px;
  color:white;
  text-decoration:none;
}

.links a:hover{
  color:#38bdf8;
}

</style>
<template>

  <Navbar/>

  <main class="container">
    <router-view/>
  </main>

</template>

<script setup>

import Navbar from "./components/Navbar.vue"

</script>

<style>

body{
  margin:0;
  font-family:Arial;
  background:#f1f5f9;
}

.container{
  padding:30px;
}

</style>
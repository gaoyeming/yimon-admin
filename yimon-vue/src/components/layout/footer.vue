<template>
    <div class="footer-span">
        <span>后台管理系统 © 2024</span>
        <span>Powered by yimon | Hello World</span>
    </div>
</template>
    
<script>
export default {
    name: "FooterView",
    data() {
        return {
        }
    },
    created() {
    },
    methods: {
    }
}
</script>

<style>
.footer-span {
    position: absolute;
    bottom: 0;
    width: 100%;

    box-sizing: border-box;
    box-shadow: -3px 0 15px 3px rgba(0, 0, 0, 0.1);
    z-index: 10;
    overflow: hidden;
    background: linear-gradient(to bottom, #d6d3d39b, #d6d3d327);
}

.footer-span>span {
    display: block;
    font-size: smaller;
    color: grey;
}
</style>
<template>
    <div class="footer">
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
.footer {
    position: absolute;
    bottom: 0;
    text-align: center;
    width: 100%;
    font-size: smaller;
    color:grey;
    box-sizing: border-box;
    box-shadow: -3px 0 15px 3px rgba(0, 0, 0, 0.1);
    z-index: 10;
    overflow: hidden;
}

span {
    display: block;
}
</style>
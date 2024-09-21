<script>
export default {
  data() {
    return {
      ws: null,
      message: '',
      serverMessage: '',
      socket: null
    };
  },
  created() {
    // 通过 URL 参数传递 Token
    const token = localStorage.getItem('token');
    this.ws = new WebSocket(`ws://localhost:8081/ws?token=${token}`);
    // webSocket不支持头部传送数据
  },
  methods: {

    connectWebSocket() {

      this.ws = new WebSocket('ws://localhost:8081/ws');
      this.ws.onmessage = (event) => {
        this.serverMessage = event.data;
      };
      this.ws.onopen = () => {
        console.log('WebSocket 连接成功');
      };
      this.ws.onclose = () => {
        console.log('WebSocket 连接关闭');
      };
      this.ws.onerror = (error) => {
        console.error('WebSocket 错误: ', error);
      };
    },
    sendMessage() {
      if (this.ws && this.message) {
        this.ws.send(this.message);
        this.message = '';
      }
    }
  },
  mounted() {
    this.connectWebSocket();
  },
  beforeUnmount() {  // 修改这里
    if (this.ws) {
      this.ws.close();
    }
  }
};
</script>

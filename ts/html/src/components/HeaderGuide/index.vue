<template>
  <div>
    <svg-icon icon-class="guide" @click.prevent.stop="guide" />
  </div>
</template>

<script>
import Driver from 'driver.js' // import driver.js
import 'driver.js/dist/driver.min.css' // import driver.js css
import steps from './steps'

export default {
  name: 'HeaderGuide',
  data() {
    return {
      driver: null
    }
  },
  mounted() {
    this.driver = new Driver({
      doneBtnText: '完成',
      closeBtnText: '关闭',
      stageBackground: '#ffffff',
      nextBtnText: '下一步',
      prevBtnText: '上一步'
    })
  },
  methods: {
    guide() {
      this.driver.defineSteps(steps)
      this.driver.start()
    }
  }
}
</script>

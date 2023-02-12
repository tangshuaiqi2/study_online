'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"http://localhost:9001"', //通过nginx转发
  BASE_API: '"http://localhost:8222"',//通过gateway网关转发
})

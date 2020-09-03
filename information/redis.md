# Redis

## 简介

1. 支持数据持久化，将内存数据保存在磁盘中，方便服务重启后再次读取。
2. 支持key-string,key-list,key-set,key-hashmap等多种数据结构的存储，常被称为数据结构服务器。
3. 读写性能极高。
4. 原子性。支持事务。

## 优势

1. 在某些数据经常被访问，但是重要到不允许丢失的场合，代替cache等来完成持久化。
2. 相比传统数据库例如mysql，读写性能更高，访问更加迅速。

## 场景

1. 代替auction map存储当前可访问的竞价条目。
2. 代替search cache，服务重启后不必重新加载。

## 使用

springboot现已支持redis，包装springboot提供的redisTemplate后即可用于实际项目。
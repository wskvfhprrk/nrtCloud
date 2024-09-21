import os

# 创建 Vue 项目的目录结构
project_structure = [
    "public",
    "public/index.html",
    "public/favicon.ico",
    "src",
    "src/api",
    "src/assets",
    "src/components",
    "src/router",
    "src/store",
    "src/store/modules",
    "src/views",
    "src/plugins",
    "src/tests",
    "src/App.vue",
    "src/main.js",
    ".env",
    "babel.config.js",
    "package.json",
    "vue.config.js"
]

# 创建文件夹和文件
for path in project_structure:
    if '.' in path:  # 如果路径包含 .，则是文件
        with open(path, 'w') as f:
            f.write('')  # 创建空文件
    else:
        os.makedirs(path, exist_ok=True)  # 创建文件夹

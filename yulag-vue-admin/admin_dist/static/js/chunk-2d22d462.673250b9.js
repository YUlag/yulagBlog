(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d22d462"],{f794:function(e,t,l){"use strict";l.r(t);var o=function(){var e=this,t=e.$createElement,l=e._self._c||t;return l("div",{staticClass:"app-container"},[l("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,inline:!0}},[l("el-form-item",{attrs:{label:"菜单名称",prop:"menuName"}},[l("el-input",{attrs:{placeholder:"请输入菜单名称",clearable:"",size:"small"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.menuName,callback:function(t){e.$set(e.queryParams,"menuName",t)},expression:"queryParams.menuName"}})],1),l("el-form-item",{attrs:{label:"状态",prop:"status"}},[l("el-select",{attrs:{placeholder:"菜单状态",clearable:"",size:"small"},model:{value:e.queryParams.status,callback:function(t){e.$set(e.queryParams,"status",t)},expression:"queryParams.status"}},[l("el-option",{key:0,attrs:{label:"正常",value:0}}),l("el-option",{key:1,attrs:{label:"停用",value:1}})],1)],1),l("el-form-item",[l("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")])],1)],1),l("el-row",{staticClass:"mb8",attrs:{gutter:10}},[l("el-col",{attrs:{span:1.5}},[l("el-button",{attrs:{type:"primary",plain:"",icon:"el-icon-plus",size:"mini"},on:{click:e.handleAdd}},[e._v("新增")])],1)],1),e.refreshTable?l("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.menuList,"row-key":"id","default-expand-all":e.isExpandAll,"tree-props":{children:"children",hasChildren:"hasChildren"}}},[l("el-table-column",{attrs:{prop:"menuName",label:"菜单名称","show-overflow-tooltip":!0,width:"160"}}),l("el-table-column",{attrs:{prop:"icon",label:"图标",align:"center",width:"100"},scopedSlots:e._u([{key:"default",fn:function(e){return[l("svg-icon",{attrs:{"icon-class":e.row.icon}})]}}],null,!1,3094025326)}),l("el-table-column",{attrs:{prop:"orderNum",label:"排序",width:"60"}}),l("el-table-column",{attrs:{prop:"perms",label:"权限标识","show-overflow-tooltip":!0}}),l("el-table-column",{attrs:{prop:"component",label:"组件路径","show-overflow-tooltip":!0}}),l("el-table-column",{attrs:{prop:"status",label:"状态",width:"80"},scopedSlots:e._u([{key:"default",fn:function(t){return[0==t.row.status?l("el-tag",{attrs:{type:"success"}},[e._v("正常")]):e._e(),1==t.row.status?l("el-tag",{attrs:{type:"danger"}},[e._v("停用")]):e._e()]}}],null,!1,3039394178)}),l("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime"},scopedSlots:e._u([{key:"default",fn:function(t){return[l("span",[e._v(e._s(e.parseTime(t.row.createTime)))])]}}],null,!1,3078210614)}),l("el-table-column",{attrs:{label:"操作",align:"center","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return[l("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-edit"},on:{click:function(l){return e.handleUpdate(t.row)}}},[e._v("修改")]),l("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-plus"},on:{click:function(l){return e.handleAdd(t.row)}}},[e._v("新增")]),l("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-delete"},on:{click:function(l){return e.handleDelete(t.row)}}},[e._v("删除")])]}}],null,!1,2587911775)})],1):e._e(),l("el-dialog",{attrs:{title:e.title,visible:e.open,width:"680px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[l("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"100px"}},[l("el-row",[l("el-col",{attrs:{span:24}},[l("el-form-item",{attrs:{label:"上级菜单"}},[l("treeselect",{attrs:{options:e.menuOptions,normalizer:e.normalizer,"show-count":!0,placeholder:"选择上级菜单"},model:{value:e.form.parentId,callback:function(t){e.$set(e.form,"parentId",t)},expression:"form.parentId"}})],1)],1),l("el-col",{attrs:{span:24}},[l("el-form-item",{attrs:{label:"菜单类型",prop:"menuType"}},[l("el-radio-group",{model:{value:e.form.menuType,callback:function(t){e.$set(e.form,"menuType",t)},expression:"form.menuType"}},[l("el-radio",{attrs:{label:"M"}},[e._v("目录")]),l("el-radio",{attrs:{label:"C"}},[e._v("菜单")]),l("el-radio",{attrs:{label:"F"}},[e._v("按钮")])],1)],1)],1),l("el-col",{attrs:{span:24}},["F"!=e.form.menuType?l("el-form-item",{attrs:{label:"菜单图标"}},[l("el-select",{staticStyle:{width:"240px"},attrs:{placeholder:"图标",clearable:"",size:"small"},model:{value:e.form.icon,callback:function(t){e.$set(e.form,"icon",t)},expression:"form.icon"}},e._l(e.icons,(function(t,o){return l("el-option",{key:o,attrs:{value:t}},[l("svg-icon",{attrs:{"icon-class":t}}),l("span",[e._v(e._s(t))])],1)})),1)],1):e._e()],1),l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"菜单名称",prop:"menuName"}},[l("el-input",{attrs:{placeholder:"请输入菜单名称"},model:{value:e.form.menuName,callback:function(t){e.$set(e.form,"menuName",t)},expression:"form.menuName"}})],1)],1),l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"显示排序",prop:"orderNum"}},[l("el-input-number",{attrs:{"controls-position":"right",min:0},model:{value:e.form.orderNum,callback:function(t){e.$set(e.form,"orderNum",t)},expression:"form.orderNum"}})],1)],1),l("el-col",{attrs:{span:24}},["F"!=e.form.menuType?l("el-form-item",{attrs:{prop:"path"}},[l("span",{attrs:{slot:"label"},slot:"label"},[l("el-tooltip",{attrs:{content:"访问的路由地址，如：`user`",placement:"top"}},[l("i",{staticClass:"el-icon-question"})]),e._v(" 路由地址 ")],1),l("el-input",{attrs:{placeholder:"请输入路由地址"},model:{value:e.form.path,callback:function(t){e.$set(e.form,"path",t)},expression:"form.path"}})],1):e._e()],1),"C"==e.form.menuType?l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{prop:"component"}},[l("span",{attrs:{slot:"label"},slot:"label"},[l("el-tooltip",{attrs:{content:"访问的组件路径，如：`system/user/index`，默认在`views`目录下",placement:"top"}},[l("i",{staticClass:"el-icon-question"})]),e._v(" 组件路径 ")],1),l("el-input",{attrs:{placeholder:"请输入组件路径"},model:{value:e.form.component,callback:function(t){e.$set(e.form,"component",t)},expression:"form.component"}})],1)],1):e._e(),l("el-col",{attrs:{span:12}},["M"!=e.form.menuType?l("el-form-item",[l("el-input",{attrs:{placeholder:"请输入权限标识",maxlength:"100"},model:{value:e.form.perms,callback:function(t){e.$set(e.form,"perms",t)},expression:"form.perms"}}),l("span",{attrs:{slot:"label"},slot:"label"},[l("el-tooltip",{attrs:{content:"控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)",placement:"top"}},[l("i",{staticClass:"el-icon-question"})]),e._v(" 权限字符 ")],1)],1):e._e()],1),l("el-col",{attrs:{span:12}},["F"!=e.form.menuType?l("el-form-item",[l("span",{attrs:{slot:"label"},slot:"label"},[l("el-tooltip",{attrs:{content:"选择隐藏则路由将不会出现在侧边栏，但仍然可以访问",placement:"top"}},[l("i",{staticClass:"el-icon-question"})]),e._v(" 显示状态 ")],1),l("el-radio-group",{model:{value:e.form.visible,callback:function(t){e.$set(e.form,"visible",t)},expression:"form.visible"}},[l("el-radio",{key:"0",attrs:{label:"0"}},[e._v("显示")]),l("el-radio",{key:"1",attrs:{label:"1"}},[e._v("隐藏")])],1)],1):e._e()],1),l("el-col",{attrs:{span:12}},["F"!=e.form.menuType?l("el-form-item",[l("span",{attrs:{slot:"label"},slot:"label"},[l("el-tooltip",{attrs:{content:"选择停用则路由将不会出现在侧边栏，也不能被访问",placement:"top"}},[l("i",{staticClass:"el-icon-question"})]),e._v(" 菜单状态 ")],1),l("el-radio-group",{model:{value:e.form.status,callback:function(t){e.$set(e.form,"status",t)},expression:"form.status"}},[l("el-radio",{key:"0",attrs:{label:"0"}},[e._v("正常")]),l("el-radio",{key:"1",attrs:{label:"1"}},[e._v("停用")])],1)],1):e._e()],1)],1)],1),l("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[l("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),l("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},n=[],a=(l("d3b7"),l("ddb0"),l("d81d"),l("ac1f"),l("466d"),l("1f27")),r=l("ca17"),s=l.n(r),i=(l("542c"),{name:"Menu",components:{Treeselect:s.a},data:function(){return{loading:!0,showSearch:!0,menuList:[],menuOptions:[],title:"",open:!1,isExpandAll:!1,refreshTable:!0,queryParams:{menuName:void 0,visible:void 0},icons:[],form:{},rules:{menuName:[{required:!0,message:"菜单名称不能为空",trigger:"blur"}],orderNum:[{required:!0,message:"菜单顺序不能为空",trigger:"blur"}],path:[{required:!0,message:"路由地址不能为空",trigger:"blur"}]}}},created:function(){this.getList(),this.icons=this.getIcons()},methods:{getIcons:function(){var e=l("23f1"),t=function(e){return e.keys()},o=/\.\/(.*)\.svg/,n=t(e).map((function(e){return e.match(o)[1]}));return n},selected:function(e){this.form.icon=e},getList:function(){var e=this;this.loading=!0,Object(a["e"])(this.queryParams).then((function(t){e.menuList=e.handleTree(t,"id"),e.loading=!1}))},normalizer:function(e){return e.children&&!e.children.length&&delete e.children,{id:e.id,label:e.menuName,children:e.children}},getTreeselect:function(){var e=this;Object(a["e"])().then((function(t){e.menuOptions=[];var l={id:0,menuName:"主类目",children:[]};l.children=e.handleTree(t,"id"),e.menuOptions.push(l)}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={id:void 0,parentId:0,menuName:void 0,icon:void 0,menuType:"M",orderNum:void 0,isCache:"0",visible:"0",status:"0"},this.resetForm("form")},handleQuery:function(){this.getList()},handleAdd:function(e){this.reset(),this.getTreeselect(),null!=e&&e.id?this.form.parentId=e.id:this.form.parentId=0,this.open=!0,this.title="添加菜单"},handleUpdate:function(e){var t=this;this.reset(),this.getTreeselect(),Object(a["c"])(e.id).then((function(e){t.form=e,t.open=!0,t.title="修改菜单"}))},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(void 0!==e.form.id?Object(a["h"])(e.form).then((function(t){e.$modal.msgSuccess("修改成功"),e.open=!1,e.getList()})):Object(a["a"])(e.form).then((function(t){e.$modal.msgSuccess("新增成功"),e.open=!1,e.getList()})))}))},handleDelete:function(e){var t=this;this.$modal.confirm('是否确认删除名称为"'+e.menuName+'"的数据项？').then((function(){return Object(a["b"])(e.id)})).then((function(){t.getList(),t.$modal.msgSuccess("删除成功")})).catch((function(){}))}}}),c=i,m=l("2877"),u=Object(m["a"])(c,o,n,!1,null,null,null);t["default"]=u.exports}}]);
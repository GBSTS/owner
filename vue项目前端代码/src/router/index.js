import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */
import componentsRouter from './modules/components'
import chartsRouter from './modules/charts'
import tableRouter from './modules/table'
import nestedRouter from './modules/nested'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 * 所有角色都可以访问
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: 'dashboard', icon: 'dashboard', affix: true }
      }
    ]
  },
  // {
  //   path: '/documentation',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/documentation/index'),
  //       name: 'Documentation',
  //       meta: { title: 'documentation', icon: 'documentation', affix: true }
  //     }
  //   ]
  // },
  // {
  //   path: '/guide',
  //   component: Layout,
  //   redirect: '/guide/index',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/guide/index'),
  //       name: 'Guide',
  //       meta: { title: 'guide', icon: 'guide', noCache: true }
  //     }
  //   ]
  // },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: 'profile', icon: 'user', noCache: true }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/teacher', //父标签路径
    component: Layout,
    // redirect: '/teacher/teacher',
    name: 'Teacher', //必须要加，名字最好取和path一样的
    meta: { 
    title: '教师管理系统', //title表示侧边栏名称
    icon: 'el-icon-s-help',  //icon表示图标
    roles: ['admin','teacher','admin_teacher','school','college','department']  
  }, 
    children: [
      {
        path: 'college',
        name: 'college',
        component: () => import('@/views/teacher/college'),
        meta: { title: '学院列表', icon: 'tree' }
      },
      {
        path: 'department',
        name: 'department',
        component: () => import('@/views/teacher/department'),
        meta: { title: '系科列表', icon: 'tree' }
      },
      {
        path: 'major', 
        name: 'major',
        component: () => import('@/views/teacher/major'), 
        meta: { title: '专业列表', icon: 'table' }
      },
      {
        path: 'departmentclass',
        name: 'Tree',
        component: () => import('@/views/teacher/departmentclass'),
        meta: { title: '行政班列表', icon: 'tree' }
      },
      {
        path: 'course', 
        name: 'course',
        component: () => import('@/views/teacher/course'), 
        meta: { title: '课程列表', icon: 'table' }
      },
      {
        path: 'courseclass',
        name: 'Tree',
        component: () => import('@/views/teacher/courseclass'),
        meta: { title: '课程班列表', icon: 'tree' }
      },
      {
        path: 'teacher', //这里会前往到/teacher/teacher
        name: 'teacher',
        component: () => import('@/views/teacher/teacher'), //给当前路由导入vue页面组件
        meta: { title: '教师列表', icon: 'table', roles: ['admin','admin_teacher','school','college','department'] }
      },
      // {
      //   path: 'teacherinfo',
      //   name: 'Tree',
      //   component: () => import('@/views/teacher/teacherinfo'),
      //   meta: { title: '测试', icon: 'tree' }
      // },
      {
        path: 'talk',
        name: 'talk',
        component: () => import('@/views/teacher/talk'),
        meta: { title: '咨询用户管理员', icon: 'tree' }
      }
    ]
  },

  {
    path: '/student',
    component: Layout,
    // redirect: '/student/studentinfo',
    name: 'student',
    meta: { title: '学生管理系统', icon: 'el-icon-s-help', roles: ['admin','student','admin_student'] },
    children: [
      {
        path: 'course',
        name: 'course',
        component: () => import('@/views/student/course'),
        meta: { title: '选课', icon: 'table', roles: ['admin', 'admin_student', 'student'] },
        
      },
      {
        path: 'studentinfo',
        name: 'Tree',
        component: () => import('@/views/student/studentinfo'),
        meta: { title: '基本信息', icon: 'tree', roles: ['student'] },

      },
      {
        path: 'student',
        name: 'Tree',
        component: () => import('@/views/student/student'),
        meta: { title: '学生列表', icon: 'tree', roles: ['admin','admin_student'] },
        
      }
    ]
  },
  {
    path: '/manager',
    component: Layout,
    redirect: '/manager/user-verify',
    name: 'manager',
    meta: { title: '管理员系统', icon: 'el-icon-s-help', roles: ['admin', 'manager'] },
    children: [
      {
        path: 'ma-verify',
        name: 'ma-verify',
        component: () => import('@/views/manager/ma-verify'),
        meta: { title: '校级用户审批', icon: 'table' }
      },
      {
        path: 'ma-maintenance',
        name: '维护服务',
        component: () => import('@/views/manager/ma-maintenance'),
        meta: { title: '维护服务', icon: 'tree' }
      },
      {
        path: 'ma-talk',
        name: 'ma-talk',
        component: () => import('@/views/manager/ma-talk'),
        meta: { title: '用户咨询', icon: 'tree' }
      }
    ]
  },

  {
    path: '/changePassword',
    component: Layout,
    redirect: '/changePassword',
    children: [
      {
        path: 'changePassword',
        component: () => import('@/views/dashboard/password'),
        name: 'Password',
        meta: { title: '修改密码', icon: 'dashboard', affix: true}
      }
    ]
  },

  {
    path: '/apply',
    component: Layout,
    redirect: '/apply',
    children: [
      {
        path: 'apply',
        component: () => import('@/views/dashboard/apply'),
        name: 'Apply',
        meta: { title: '角色申请', icon: 'dashboard', affix: true, roles: ['admin', 'teacher', 'common_teacher','admin_teacher','school','college','department'] }
      }
    ]
  },

  {
    path: '/repair',
    component: Layout,
    redirect: '/repair',
    children: [
      {
        path: 'repair',
        component: () => import('@/views/dashboard/repair'),
        name: 'Repair',
        meta: { title: '维修申请', icon: 'dashboard', affix: true, roles: ['admin', 'admin_teacher','school'] }
      }
    ]
  },


  // {  
  //   path: '/permission',
  //   component: Layout,
  //   redirect: '/permission/page',
  //   alwaysShow: true, // will always show the root menu
  //   name: 'Permission',
  //   meta: {
  //     title: 'permission',
  //     icon: 'lock',
  //     roles: ['admin', 'editor'] // you can set roles in root nav
  //   },
  //   children: [
  //     {
  //       path: 'page',
  //       component: () => import('@/views/permission/page'),
  //       name: 'PagePermission',
  //       meta: {
  //         title: 'pagePermission',
  //         roles: ['admin'] // or you can only set roles in sub nav
  //       }
  //     },
  //     {
  //       path: 'directive',
  //       component: () => import('@/views/permission/directive'),
  //       name: 'DirectivePermission',
  //       meta: {
  //         title: 'directivePermission'
  //         // if do not set roles, means: this page does not require permission
  //       }
  //     },
  //     {
  //       path: 'role',
  //       component: () => import('@/views/permission/role'),
  //       name: 'RolePermission',
  //       meta: {
  //         title: 'rolePermission',
  //         roles: ['admin']
  //       }
  //     }
  //   ]
  // },


  // {
  //   path: '/icon',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/icons/index'),
  //       name: 'Icons',
  //       meta: { title: 'icons', icon: 'icon', noCache: true }
  //     }
  //   ]
  // },

  // /** when your routing map is too long, you can split it into small modules **/
  // componentsRouter,
  // chartsRouter,
  // nestedRouter,
  // tableRouter,

  // {
  //   path: '/example',
  //   component: Layout,
  //   redirect: '/example/list',
  //   name: 'Example',
  //   meta: {
  //     title: 'example',
  //     icon: 'el-icon-s-help'
  //   },
  //   children: [
  //     {
  //       path: 'create',
  //       component: () => import('@/views/example/create'),
  //       name: 'CreateArticle',
  //       meta: { title: 'createArticle', icon: 'edit' }
  //     },
  //     {
  //       path: 'edit/:id(\\d+)',
  //       component: () => import('@/views/example/edit'),
  //       name: 'EditArticle',
  //       meta: { title: 'editArticle', noCache: true, activeMenu: '/example/list' },
  //       hidden: true
  //     },
  //     {
  //       path: 'list',
  //       component: () => import('@/views/example/list'),
  //       name: 'ArticleList',
  //       meta: { title: 'articleList', icon: 'list' }
  //     }
  //   ]
  // },

  // {
  //   path: '/tab',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/tab/index'),
  //       name: 'Tab',
  //       meta: { title: 'tab', icon: 'tab' }
  //     }
  //   ]
  // },

  // {
  //   path: '/error',
  //   component: Layout,
  //   redirect: 'noRedirect',
  //   name: 'ErrorPages',
  //   meta: {
  //     title: 'errorPages',
  //     icon: '404'
  //   },
  //   children: [
  //     {
  //       path: '401',
  //       component: () => import('@/views/error-page/401'),
  //       name: 'Page401',
  //       meta: { title: 'page401', noCache: true }
  //     },
  //     {
  //       path: '404',
  //       component: () => import('@/views/error-page/404'),
  //       name: 'Page404',
  //       meta: { title: 'page404', noCache: true }
  //     }
  //   ]
  // },

  // {
  //   path: '/error-log',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'log',
  //       component: () => import('@/views/error-log/index'),
  //       name: 'ErrorLog',
  //       meta: { title: 'errorLog', icon: 'bug' }
  //     }
  //   ]
  // },

  // {
  //   path: '/excel',
  //   component: Layout,
  //   redirect: '/excel/export-excel',
  //   name: 'Excel',
  //   meta: {
  //     title: 'excel',
  //     icon: 'excel'
  //   },
  //   children: [
  //     {
  //       path: 'export-excel',
  //       component: () => import('@/views/excel/export-excel'),
  //       name: 'ExportExcel',
  //       meta: { title: 'exportExcel' }
  //     },
  //     {
  //       path: 'export-selected-excel',
  //       component: () => import('@/views/excel/select-excel'),
  //       name: 'SelectExcel',
  //       meta: { title: 'selectExcel' }
  //     },
  //     {
  //       path: 'export-merge-header',
  //       component: () => import('@/views/excel/merge-header'),
  //       name: 'MergeHeader',
  //       meta: { title: 'mergeHeader' }
  //     },
  //     {
  //       path: 'upload-excel',
  //       component: () => import('@/views/excel/upload-excel'),
  //       name: 'UploadExcel',
  //       meta: { title: 'uploadExcel' }
  //     }
  //   ]
  // },

  // {
  //   path: '/zip',
  //   component: Layout,
  //   redirect: '/zip/download',
  //   alwaysShow: true,
  //   name: 'Zip',
  //   meta: { title: 'zip', icon: 'zip' },
  //   children: [
  //     {
  //       path: 'download',
  //       component: () => import('@/views/zip/index'),
  //       name: 'ExportZip',
  //       meta: { title: 'exportZip' }
  //     }
  //   ]
  // },

  // {
  //   path: '/pdf',
  //   component: Layout,
  //   redirect: '/pdf/index',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/pdf/index'),
  //       name: 'PDF',
  //       meta: { title: 'pdf', icon: 'pdf' }
  //     }
  //   ]
  // },
  // {
  //   path: '/pdf/download',
  //   component: () => import('@/views/pdf/download'),
  //   hidden: true
  // },

  // {
  //   path: '/theme',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/theme/index'),
  //       name: 'Theme',
  //       meta: { title: 'theme', icon: 'theme' }
  //     }
  //   ]
  // },

  // {
  //   path: '/clipboard',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/clipboard/index'),
  //       name: 'ClipboardDemo',
  //       meta: { title: 'clipboardDemo', icon: 'clipboard' }
  //     }
  //   ]
  // },

  // {
  //   path: '/i18n',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/i18n-demo/index'),
  //       name: 'I18n',
  //       meta: { title: 'i18n', icon: 'international' }
  //     }
  //   ]
  // },

  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://github.com/PanJiaChen/vue-element-admin',
  //       meta: { title: 'externalLink', icon: 'link' }
  //     }
  //   ]
  // },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router

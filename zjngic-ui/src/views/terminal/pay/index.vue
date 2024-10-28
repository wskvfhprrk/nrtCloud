<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付订单号" prop="outTradeNo">
        <el-input
          v-model="queryParams.outTradeNo"
          placeholder="请输入支付订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付金额" prop="payAmount">
        <el-input
          v-model="queryParams.payAmount"
          placeholder="请输入支付金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付方式" prop="payMethod">
        <el-input
          v-model="queryParams.payMethod"
          placeholder="请输入支付方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否退款" prop="isRefunded">
        <el-input
          v-model="queryParams.isRefunded"
          placeholder="请输入是否退款"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款编号" prop="refundCode">
        <el-input
          v-model="queryParams.refundCode"
          placeholder="请输入退款编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="paymentStatus">
        <el-select v-model="queryParams.paymentStatus" placeholder="请选择订单状态" clearable>
          <el-option
            v-for="dict in dict.type.order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="支付时间" prop="payTime">
        <el-date-picker clearable
          v-model="queryParams.payTime"
          type="date"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择支付时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="退款时间" prop="refundTime">
        <el-date-picker clearable
          v-model="queryParams.refundTime"
          type="date"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择退款时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="退款方式" prop="refundMethod">
        <el-input
          v-model="queryParams.refundMethod"
          placeholder="请输入退款方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['terminal:pay:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['terminal:pay:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['terminal:pay:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['terminal:pay:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="payList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="支付记录ID" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="支付订单号" align="center" prop="outTradeNo" />
      <el-table-column label="机器编号" align="center" prop="machineCode" />
      <el-table-column label="支付金额" align="center" prop="payAmount" />
      <el-table-column label="支付方式" align="center" prop="payMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_method" :value="scope.row.payMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="是否退款" align="center" prop="isRefunded" />
      <el-table-column label="退款编号" align="center" prop="refundCode" />
      <el-table-column label="订单状态" align="center" prop="paymentStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.paymentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="支付时间" align="center" prop="payTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime, '{y}-{m}-{d}') }} {{parseTime(scope.row.payTime, '{h}:{m}:{s}')}}</span>
        </template>
      </el-table-column>
      <el-table-column label="退款时间" align="center" prop="refundTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.refundTime, '{y}-{m}-{d}') }} {{parseTime(scope.row.refundTime, '{h}:{m}:{s}')}}</span>
        </template>
      </el-table-column>
      <el-table-column label="退款方式" align="center" prop="refundMethod" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['terminal:pay:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['terminal:pay:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单支付对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="支付订单号" prop="outTradeNo">
          <el-input v-model="form.outTradeNo" placeholder="请输入支付订单号" />
        </el-form-item>
        <el-form-item label="支付金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="支付方式" prop="payMethod">
          <el-input v-model="form.payMethod" placeholder="请输入支付方式" />
        </el-form-item>
        <el-form-item label="是否退款" prop="isRefunded">
          <el-input v-model="form.isRefunded" placeholder="请输入是否退款" />
        </el-form-item>
        <el-form-item label="退款编号" prop="refundCode">
          <el-input v-model="form.refundCode" placeholder="请输入退款编号" />
        </el-form-item>
        <el-form-item label="订单状态" prop="paymentStatus">
          <el-radio-group v-model="form.paymentStatus">
            <el-radio
              v-for="dict in dict.type.order_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="支付时间" prop="payTime">
          <el-date-picker clearable
            v-model="form.payTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择支付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="退款时间" prop="refundTime">
          <el-date-picker clearable
            v-model="form.refundTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择退款时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="退款方式" prop="refundMethod">
          <el-input v-model="form.refundMethod" placeholder="请输入退款方式" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPay, getPay, delPay, addPay, updatePay } from "@/api/terminal/pay";

export default {
  name: "Pay",
  dicts: ['order_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单支付表格数据
      payList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        outTradeNo: null,
        machineCode: null,
        payAmount: null,
        payMethod: null,
        isRefunded: null,
        refundCode: null,
        paymentStatus: null,
        payTime: null,
        refundTime: null,
        refundMethod: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        outTradeNo: [
          { required: true, message: "支付订单号不能为空", trigger: "blur" }
        ],
        machineCode: [
          { required: true, message: "机器编号不能为空", trigger: "change" }
        ],
        payAmount: [
          { required: true, message: "支付金额不能为空", trigger: "blur" }
        ],
        payMethod: [
          { required: true, message: "支付方式不能为空", trigger: "blur" }
        ],
        paymentStatus: [
          { required: true, message: "订单状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单支付列表 */
    getList() {
      this.loading = true;
      listPay(this.queryParams).then(response => {
        this.payList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderId: null,
        outTradeNo: null,
        machineCode: null,
        payAmount: null,
        payMethod: null,
        isRefunded: null,
        refundCode: null,
        paymentStatus: null,
        payTime: null,
        refundTime: null,
        refundMethod: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单支付";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPay(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单支付";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePay(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPay(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单支付编号为"' + ids + '"的数据项？').then(function() {
        return delPay(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('terminal/pay/export', {
        ...this.queryParams
      }, `pay_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

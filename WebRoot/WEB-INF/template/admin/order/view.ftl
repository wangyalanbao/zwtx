<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${message("admin.order.view")}</title>


    <link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/jquery.lSelect.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
    <script type="text/javascript">
        $().ready(function() {

            var $confirmForm = $("#confirmForm");
            var $completeForm = $("#completeForm");
            var $cancelForm = $("#cancelForm");
            var $confirmButton = $("#confirmButton");
            var $paymentButton = $("#paymentButton");
            var $shippingButton = $("#shippingButton");
            var $completeButton = $("#completeButton");
            var $refundsButton = $("#refundsButton");
            var $returnsButton = $("#returnsButton");
            var $cancelButton = $("#cancelButton");
            var $shippingEdit = $("#shippingEdit");
            var isLocked = false;

        [@flash_message /]

            // 检查锁定
            function checkLock() {
                if (!isLocked) {
                    $.ajax({
                        url: "check_lock.jhtml",
                        type: "POST",
                        data: {id: ${order.id}},
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            if (message.type != "success") {
                                $.message(message);
                                $confirmButton.prop("disabled", true);
                                $paymentButton.prop("disabled", true);
                                $shippingButton.prop("disabled", true);
                                $completeButton.prop("disabled", true);
                                $refundsButton.prop("disabled", true);
                                $returnsButton.prop("disabled", true);
                                $cancelButton.prop("disabled", true);
                                isLocked = true;
                            }
                        }
                    });
                }
            }

            // 检查锁定
            checkLock();
            setInterval(function() {
                checkLock();
            }, 10000);

            // 确认
            $confirmButton.click(function() {
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "${message("admin.order.confirmDialog")}",
                    onOk: function() {
                        $confirmForm.submit();
                    }
                });
            });

            // 完成
            $completeButton.click(function() {
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "${message("admin.order.completeDialog")}",
                    onOk: function() {
                        $completeForm.submit();
                    }
                });
            });

            // 取消
            $cancelButton.click(function() {
                var $this = $(this);
                $.dialog({
                    type: "warn",
                    content: "${message("admin.order.cancelDialog")}",
                    onOk: function() {
                        $cancelForm.submit();
                    }
                });
            });

            // 修改发票
            $shippingEdit.click(function() {
                    $.dialog({
                    title: "${message("admin.order.shipping")}",
            [@compress single_line = true]
                    content: '
            <form id="shippingForm" action="shipping.jhtml" method="post">
            <input type="hidden" name="token" value="${token}" \/>
                <input type="hidden" name="orderId" value="${order.id}" \/>
                <input type="hidden" name="id" value="${shipping.id}" \/>
                <div style="height: 258px; overflow-x: hidden; overflow-y: auto;">
            <table class="input" >
            <tr>
            <th>
            ${message("Order.sn")}:
                <\/th>
            <td width="300">
            ${order.sn}
            <\/td>
            <th>
            ${message("admin.common.createDate")}:
                <\/th>
            <td>
            ${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.shippingMethod")}:
                <\/th>
            <td>
            <select name="shippingMethodId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list shippingMethods as shippingMethod]
                <option value="${shippingMethod.id}" [#if shippingMethod.name == shipping.shippingMethod] selected="selected"[/#if] >${shippingMethod.name}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <th>
            ${message("Shipping.consignee")}:
                <\/th>
            <td>
            <input type="text" name="consignee" class="text" value="${shipping.consignee}"  maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.trackingNo")}:
                <\/th>
            <td>
            <input type="text" name="trackingNo" class="text"  value="${shipping.trackingNo}"  maxlength="200" \/>
                <\/td>
            <th>
            ${message("Shipping.zipCode")}:
                <\/th>
            <td>
            <input type="text" name="zipCode" class="text"  value="${shipping.zipCode}"  maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.area")}:
                <\/th>
            <td>
            <span class="fieldSet">
            <input type="hidden" id="areaId" name="areaId" value="${(shipping.area.id)!}" treePath="${(shipping.area.treePath)!}"  \/>
                <\/span>
            <\/td>
            <th>
            ${message("Shipping.address")}:
                <\/th>
            <td>
            <input type="text" name="address" class="text"  value="${shipping.address}"   maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.phone")}:
                <\/th>
            <td>
            <input type="text" name="phone" class="text"   value="${shipping.phone}" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Order.invoiceTitle")}:
                <\/th>
            <td  colspan="3">
            <input type="text" name="invoiceTitle" class="text"  value="${shipping.invoiceTitle}" maxlength="600"  style="width: 650px;" \/>
                <\/td>

            <\/tr>
            <tr>
            <th>
            ${message("Order.memo")}:
                <\/th>
            <td  colspan="3">
            <input type="text" name="memo" class="text"  value="${shipping.memo}"  maxlength="600" style="width: 650px;" \/>
                <\/td>
            <\/tr>
            <\/table>
            <\/div>
            <\/form>',
            [/@compress]
                width: 900,
                        modal: true,
                        ok: "${message("admin.dialog.ok")}",
                        cancel: "${message("admin.dialog.cancel")}",
                        onShow: function() {
                    $("#areaId").lSelect({
                        url: "${base}/common/area.jhtml"
                    });
                    $.validator.addClassRules({
                        shippingItemsQuantity: {
                            required: true,
                            digits: true
                        }
                    });
                    $("#shippingForm").validate({
                        rules: {
                            shippingMethodId: "required",
                            deliveryCorpId: "required",
                            freight: {
                                min: 0,
                                decimal: {
                                    integer: 12,
                                    fraction: ${setting.priceScale}
                                }
                            },
                            consignee: "required",
                            zipCode: "required",
                            areaId: "required",
                            address: "required",
                            phone: "required"
                        }
                    });
                },
                onOk: function() {
                    $("#shippingForm").submit();
                    return false;
                }
            });
        });

        [#if !order.expired && order.orderStatus == "confirmed" && (order.paymentStatus == "unpaid" || order.paymentStatus == "partialPayment")]
        // 支付
        $paymentButton.click(function() {
                $.dialog({
                title: "${message("admin.order.payment")}",
            [@compress single_line = true]
                    content: '
            <form id="paymentForm" action="payment.jhtml" method="post">
            <input type="hidden" name="token" value="${token}" \/>
                <input type="hidden" name="orderId" value="${order.id}" \/>
                <table class="input">
            <tr>
            <th>
            ${message("Order.sn")}:
                <\/th>
            <td width="300">
            ${order.sn}
            <\/td>
            <th>
            ${message("admin.common.createDate")}:
                <\/th>
            <td>
            ${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Order.amount")}:
                <\/th>
            <td>
            ${currency(order.amount, true)}
            <\/td>
            <th>
            ${message("Order.amountPaid")}:
                <\/th>
            <td>
            ${currency(order.amountPaid, true)}
            <\/td>
            <\/tr>
                [#if order.isInvoice]
                <tr>
                <th>
                ${message("Order.invoiceTitle")}:
                    <\/th>
                <td>
                ${order.invoiceTitle}
                <\/td>
                <th>
                ${message("Order.tax")}:
                    <\/th>
                <td>
                ${currency(order.tax, true)}
                <\/td>
                <\/tr>
                [/#if]
            <tr>
            <th>
            ${message("Payment.bank")}:
                <\/th>
            <td>
            <input type="text" name="bank" class="text" maxlength="200" \/>
                <\/td>
            <th>
            ${message("Payment.account")}:
                <\/th>
            <td>
            <input type="text" name="account" class="text" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Payment.amount")}:
                <\/th>
            <td>
            <input type="text" name="amount" class="text" value="${order.amountPayable}" maxlength="16" \/>
                <\/td>
            <th>
            ${message("Payment.payer")}:
                <\/th>
            <td>
            <input type="text" name="payer" class="text" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Payment.method")}:
                <\/th>
            <td>
            <select id="method" name="method">
                [#list methods as method]
                <option value="${method}">${message("Payment.Method." + method)}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <th>
            ${message("Payment.paymentMethod")}:
                <\/th>
            <td>
            <select name="paymentMethodId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list paymentMethods as paymentMethod]
                <option value="${paymentMethod.id}">${paymentMethod.name}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Payment.memo")}:
                <\/th>
            <td colspan="3">
            <input type="text" name="memo" class="text" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <td colspan="4" style="border-bottom: none;">
            &nbsp;
                <\/td>
            <\/tr>
            <\/table>
            <\/form>',
            [/@compress]
            width: 900,
                    modal: true,
                    ok: "${message("admin.dialog.ok")}",
                    cancel: "${message("admin.dialog.cancel")}",
                    onShow: function() {
                var $method = $("#method");
                $.validator.addMethod("balance",
                        function(value, element, param) {
                            return this.optional(element) || $method.val() != "deposit" || parseFloat(value) <= parseFloat(param);
                        },
                        "${message("admin.order.insufficientBalance")}"
                );
                $("#paymentForm").validate({
                    rules: {
                        amount: {
                            required: true,
                            positive: true,
                            max: ${order.amountPayable},
                            decimal: {
                                integer: 12,
                                fraction: ${setting.priceScale}
                            },
                            balance: ${order.member.balance}
                        }
                    }
                });
            },
            onOk: function() {
                $("#paymentForm").submit();
                return false;
            }
        });
        });
        [/#if]

        [#if !order.expired && order.orderStatus == "confirmed" && (order.paymentStatus == "paid" || order.paymentStatus == "partialPayment" || order.paymentStatus == "partialRefunds")]
        // 退款
        $refundsButton.click(function() {
                $.dialog({
                title: "${message("admin.order.refunds")}",
            [@compress single_line = true]
                    content: '
            <form id="refundsForm" action="refunds.jhtml" method="post">
            <input type="hidden" name="token" value="${token}" \/>
                <input type="hidden" name="orderId" value="${order.id}" \/>
                <table class="input">
            <tr>
            <th>
            ${message("Order.sn")}:
                <\/th>
            <td width="300">
            ${order.sn}
            <\/td>
            <th>
            ${message("admin.common.createDate")}:
                <\/th>
            <td>
            ${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Order.amount")}:
                <\/th>
            <td>
            ${currency(order.amount, true)}
            <\/td>
            <th>
            ${message("Order.amountPaid")}:
                <\/th>
            <td>
            ${currency(order.amountPaid, true)}
            <\/td>
            <\/tr>
                [#if order.isInvoice]
                <tr>
                <th>
                ${message("Order.invoiceTitle")}:
                    <\/th>
                <td>
                ${order.invoiceTitle}
                <\/td>
                <th>
                ${message("Order.tax")}:
                    <\/th>
                <td>
                ${currency(order.tax, true)}
                <\/td>
                <\/tr>
                [/#if]
            <tr>
            <th>
            ${message("Refunds.bank")}:
                <\/th>
            <td>
            <input type="text" name="bank" class="text" maxlength="200" \/>
                <\/td>
            <th>
            ${message("Refunds.account")}:
                <\/th>
            <td>
            <input type="text" name="account" class="text" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Refunds.amount")}:
                <\/th>
            <td>
            <input type="text" name="amount" class="text" value="${order.amountPaid}" maxlength="16" \/>
                <\/td>
            <th>
            ${message("Refunds.payee")}:
                <\/th>
            <td>
            <input type="text" name="payee" class="text" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Refunds.method")}:
                <\/th>
            <td>
            <select name="method">
                [#list refundsMethods as refundsMethod]
                <option value="${refundsMethod}">${message("Refunds.Method." + refundsMethod)}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <th>
            ${message("Refunds.paymentMethod")}:
                <\/th>
            <td>
            <select name="paymentMethodId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list paymentMethods as paymentMethod]
                <option value="${paymentMethod.id}">${paymentMethod.name}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Refunds.memo")}:
                <\/th>
            <td colspan="3">
            <input type="text" name="memo" class="text" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <td colspan="4" style="border-bottom: none;">
            &nbsp;
                <\/td>
            <\/tr>
            <\/table>
            <\/form>',
            [/@compress]
            width: 900,
                    modal: true,
                    ok: "${message("admin.dialog.ok")}",
                    cancel: "${message("admin.dialog.cancel")}",
                    onShow: function() {
                $("#refundsForm").validate({
                    rules: {
                        amount: {
                            required: true,
                            positive: true,
                            max: ${order.amountPaid},
                            decimal: {
                                integer: 12,
                                fraction: ${setting.priceScale}
                            }
                        }
                    }
                });
            },
            onOk: function() {
                $("#refundsForm").submit();
                return false;
            }
        });
        });
        [/#if]



        [#if !order.expired && order.orderStatus == "confirmed" && (order.shippingStatus == "unshipped" || order.shippingStatus == "partialShipment")]
        // 发票寄送
        $shippingButton.click(function() {
                $.dialog({
                title: "${message("admin.order.shipping")}",
            [@compress single_line = true]
                    content: '
            <form id="shippingForm" action="shipping.jhtml" method="post">
            <input type="hidden" name="token" value="${token}" \/>
                <input type="hidden" name="orderId" value="${order.id}" \/>
                <input type="hidden" name="id" value="${shipping.id}" \/>
                <div style="height: 258px; overflow-x: hidden; overflow-y: auto;">
            <table class="input" >
            <tr>
            <th>
            ${message("Order.sn")}:
                <\/th>
            <td width="300">
            ${order.sn}
            <\/td>
            <th>
            ${message("admin.common.createDate")}:
                <\/th>
            <td>
            ${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.shippingMethod")}:
                <\/th>
            <td>
            <select name="shippingMethodId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list shippingMethods as shippingMethod]
                <option value="${shippingMethod.id}" [#if shippingMethod.name == shipping.shippingMethod] select="select"[/#if] >${shippingMethod.name}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <th>
            ${message("Shipping.consignee")}:
                <\/th>
            <td>
            <input type="text" name="consignee" class="text" value="${shipping.consignee}"  maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.trackingNo")}:
                <\/th>
            <td>
            <input type="text" name="trackingNo" class="text"  value="${shipping.trackingNo}"  maxlength="200" \/>
                <\/td>
            <th>
            ${message("Shipping.zipCode")}:
                <\/th>
            <td>
            <input type="text" name="zipCode" class="text"  value="${shipping.zipCode}"  maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.area")}:
                <\/th>
            <td>
            <span class="fieldSet">
            <input type="hidden" id="areaId" name="areaId" value="${(shipping.area.id)!}" treePath="${(shipping.area.treePath)!}"  \/>
                <\/span>
            <\/td>
            <th>
            ${message("Shipping.address")}:
                <\/th>
            <td>
            <input type="text" name="address" class="text"  value="${shipping.address}"   maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Shipping.phone")}:
                <\/th>
            <td>
            <input type="text" name="phone" class="text"   value="${shipping.phone}" maxlength="200" \/>
                <\/td>
            <th>
            ${message("Shipping.deliveryCorp")}:
                <\/th>
            <td>
            <select name="deliveryCorpId">
                [#list deliveryCorps as deliveryCorp]
                <option value="${deliveryCorp.id}"  [#if deliveryCorp.name == shipping.deliveryCorp] select="select"[/#if]>${deliveryCorp.name}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Order.invoiceTitle")}:
                <\/th>
            <td  colspan="3">
            <input type="text" name="invoiceTitle" class="text"  value="${shipping.invoiceTitle}" maxlength="600"  style="width: 650px;" \/>
                <\/td>

            <\/tr>
            <tr>
            <th>
            ${message("Order.memo")}:
                <\/th>
            <td  colspan="3">
            <input type="text" name="memo" class="text"  value="${shipping.memo}"  maxlength="600" style="width: 650px;" \/>
                <\/td>
            <\/tr>
            <\/table>
            <\/div>
            <\/form>',
            [/@compress]
            width: 900,
                    modal: true,
                    ok: "${message("admin.dialog.ok")}",
                    cancel: "${message("admin.dialog.cancel")}",
                    onShow: function() {
                $("#areaId").lSelect({
                    url: "${base}/common/area.jhtml"
                });
                $.validator.addClassRules({
                    shippingItemsQuantity: {
                        required: true,
                        digits: true
                    }
                });
                $("#shippingForm").validate({
                    rules: {
                        shippingMethodId: "required",
                        deliveryCorpId: "required",
                        freight: {
                            min: 0,
                            decimal: {
                                integer: 12,
                                fraction: ${setting.priceScale}
                            }
                        },
                        consignee: "required",
                        zipCode: "required",
                        areaId: "required",
                        address: "required",
                        phone: "required"
                    }
                });
            },
            onOk: function() {
                $("#shippingForm").submit();
                return false;
            }
        });
        });
        [/#if]

        [#if !order.expired && order.orderStatus == "confirmed" && (order.shippingStatus == "shipped" || order.shippingStatus == "partialShipment" || order.shippingStatus == "partialReturns")]
        // 退货
        $returnsButton.click(function() {
                $.dialog({
                title: "${message("admin.order.returns")}",
            [@compress single_line = true]
                    content: '
            <form id="returnsForm" action="returns.jhtml" method="post">
            <input type="hidden" name="token" value="${token}" \/>
                <input type="hidden" name="orderId" value="${order.id}" \/>
                <div style="height: 380px; overflow-x: hidden; overflow-y: auto;">
            <table class="input" style="margin-bottom: 30px;">
            <tr>
            <th>
            ${message("Order.sn")}:
                <\/th>
            <td width="300">
            ${order.sn}
            <\/td>
            <th>
            ${message("admin.common.createDate")}:
                <\/th>
            <td>
            ${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Returns.shippingMethod")}:
                <\/th>
            <td>
            <select name="shippingMethodId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list shippingMethods as shippingMethod]
                <option value="${shippingMethod.id}">${shippingMethod.name}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <th>
            ${message("Returns.deliveryCorp")}:
                <\/th>
            <td>
            <select name="deliveryCorpId">
            <option value="">${message("admin.common.choose")}<\/option>
                [#list deliveryCorps as deliveryCorp]
                <option value="${deliveryCorp.id}">${deliveryCorp.name}<\/option>
                [/#list]
            <\/select>
            <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Returns.trackingNo")}:
                <\/th>
            <td>
            <input type="text" name="trackingNo" class="text" maxlength="200" \/>
                <\/td>
            <th>
            ${message("Returns.freight")}:
                <\/th>
            <td>
            <input type="text" name="freight" class="text" maxlength="16" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Returns.shipper")}:
                <\/th>
            <td>
            <input type="text" name="shipper" class="text" value="${order.consignee}" maxlength="200" \/>
                <\/td>
            <th>
            ${message("Returns.zipCode")}:
                <\/th>
            <td>
            <input type="text" name="zipCode" class="text" value="${order.zipCode}" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Returns.area")}:
                <\/th>
            <td>
            <span class="fieldSet">
            <input type="hidden" id="areaId" name="areaId" value="${(order.area.id)!}" treePath="${(order.area.treePath)!}" \/>
                <\/span>
            <\/td>
            <th>
            ${message("Returns.address")}:
                <\/th>
            <td>
            <input type="text" name="address" class="text" value="${order.address}" maxlength="200" \/>
                <\/td>
            <\/tr>
            <tr>
            <th>
            ${message("Returns.phone")}:
                <\/th>
            <td>
            <input type="text" name="phone" class="text" value="${order.phone}" maxlength="200" \/>
                <\/td>
            <th>
            ${message("Returns.memo")}:
                <\/th>
            <td>
            <input type="text" name="memo" class="text" maxlength="200" \/>
                <\/td>
            <\/tr>
            <\/table>
            <table class="input">
            <tr class="title">
            <th>
            ${message("ReturnsItem.sn")}
            <\/th>
            <th>
            ${message("ReturnsItem.name")}
            <\/th>
            <th>
            ${message("admin.order.productStock")}
            <\/th>
            <th>
            ${message("OrderItem.shippedQuantity")}
            <\/th>
            <th>
            ${message("OrderItem.returnQuantity")}
            <\/th>
            <th>
            ${message("admin.order.returnsQuantity")}
            <\/th>
            <\/tr>
                [#list order.orderItems as orderItem]
                <tr>
                <td>
                <input type="hidden" name="returnsItems[${orderItem_index}].sn" value="${orderItem.sn}" \/>
                ${orderItem.sn}
                    <\/td>
                <td width="300">
                <span title="${orderItem.fullName}">${abbreviate(orderItem.fullName, 50, "...")}<\/span>
                    [#if orderItem.isGift]
                    <span class="red">[${message("admin.order.gift")}]<\/span>
                    [/#if]
                <\/td>
                <td>
                ${(orderItem.product.stock)!"-"}
                <\/td>
                <td>
                ${orderItem.shippedQuantity}
                <\/td>
                <td>
                ${orderItem.returnQuantity}
                <\/td>
                <td>
                <input type="text" name="returnsItems[${orderItem_index}].quantity" class="text returnsItemsQuantity" value="${orderItem.shippedQuantity - orderItem.returnQuantity}" maxlength="9" style="width: 30px;"[#if orderItem.shippedQuantity - orderItem.returnQuantity <= 0] disabled="disabled"[#else] max="${orderItem.shippedQuantity - orderItem.returnQuantity}"[/#if] \/>
                    <\/td>
                <\/tr>
                [/#list]
            <tr>
            <td colspan="6" style="border-bottom: none;">
            &nbsp;
                <\/td>
            <\/tr>
            <\/table>
            <\/div>
            <\/form>',
            [/@compress]
            width: 900,
                    modal: true,
                    ok: "${message("admin.dialog.ok")}",
                    cancel: "${message("admin.dialog.cancel")}",
                    onShow: function() {
                $("#areaId").lSelect({
                    url: "${base}/common/area.jhtml"
                });
                $.validator.addClassRules({
                    returnsItemsQuantity: {
                        required: true,
                        digits: true
                    }
                });
                $("#returnsForm").validate({
                    rules: {
                        freight: {
                            min: 0,
                            decimal: {
                                integer: 12,
                                fraction: ${setting.priceScale}
                            }
                        },
                        shipper: "required",
                        zipCode: "required",
                        areaId: "required",
                        address: "required",
                        phone: "required"
                    }
                });
            },
            onOk: function() {
                var total = 0;
                $("#returnsForm input.returnsItemsQuantity").each(function() {
                    var quantity = $(this).val();
                    if ($.isNumeric(quantity)) {
                        total += parseInt(quantity);
                    }
                });
                if (total <= 0) {
                    $.message("warn", "${message("admin.order.returnsQuantityPositive")}");
                } else {
                    $("#returnsForm").submit();
                }
                return false;
            }
        });
        });
        [/#if]

        });


    </script>
    <style type="text/css">
        td a.delete{
            height: 26px;
            display: inline-block;
            display: -moz-inline-stack;
            padding: 0px 14px;
            margin-right: 10px;
            color: #444444;
            cursor: pointer;
            text-shadow: 1px 1px #ffffff;
            -webkit-box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
            -moz-box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
            box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
            outline: none;
            blr: expression(this.hideFocus = true);
            border: 1px solid #b7c8d9;
            background: #b1392a;
        }

        td a.edit{
            height: 26px;
            display: inline-block;
            display: -moz-inline-stack;
            padding: 0px 14px;
            margin-right: 10px;
            color: #444444;
            cursor: pointer;
            text-shadow: 1px 1px #ffffff;
            -webkit-box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
            -moz-box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
            box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
            outline: none;
            blr: expression(this.hideFocus = true);
            border: 1px solid #b7c8d9;
            background: #2298dd;
        }
    </style>
</head>
<body>
<form id="confirmForm" action="confirm.jhtml" method="post">
    <input type="hidden" name="id" value="${order.id}" />
</form>
<form id="completeForm" action="complete.jhtml" method="post">
    <input type="hidden" name="id" value="${order.id}" />
</form>
<form id="cancelForm" action="cancel.jhtml" method="post">
    <input type="hidden" name="id" value="${order.id}" />
</form>
<div class="path">
    <a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.order.view")}
</div>
<ul id="tab" class="tab">
    <li>
        <input type="button" value="${message("admin.order.orderInfo")}" />
    </li>
    <li>
        <input type="button" value="${message("OrderFlight.info")}" />
    </li>
    <li>
        <input type="button" value="${message("admin.order.paymentInfo")}" />
    </li>
    <li>
        <input type="button" value="${message("admin.order.invoiceInfo")}" />
    </li>
    <li>
        <input type="button" value="${message("admin.order.refundsInfo")}" />
    </li>
    <li>
        <input type="button" value="${message("admin.order.memoInfo")}" />
    </li>
    <li>
        <input type="button" value="${message("admin.order.orderLog")}" />
    </li>
</ul>
[#--订单信息--]
<div class="tabContent">
    <table class="input ">
        <tr>
            <td>
                &nbsp;
            </td>
            <td>
                <input type="button" id="confirmButton" class="button" value="${message("admin.order.confirm")}"[#if order.expired || order.orderStatus != "unconfirmed"] disabled="disabled"[/#if] />
                <input type="button" id="paymentButton" class="button" value="${message("admin.order.payment")}"[#if order.expired || order.orderStatus != "confirmed" || (order.paymentStatus != "unpaid" && order.paymentStatus != "partialPayment")] disabled="disabled"[/#if] />
                <input type="button" id="shippingButton" class="button" value="${message("admin.order.invoiceShipping")}"[#if order.expired || order.orderStatus != "confirmed" || (order.shippingStatus != "unshipped" && order.shippingStatus != "partialShipment")] disabled="disabled"[/#if] />
                <input type="button" id="completeButton" class="button" value="${message("admin.order.complete")}"[#if order.expired || order.orderStatus != "confirmed"] disabled="disabled"[/#if] />
            </td>
            <td>
                &nbsp;
            </td>
            <td>
                <input type="button" id="refundsButton" class="button" value="${message("admin.order.refunds")}"[#if order.expired || order.orderStatus != "confirmed" || (order.paymentStatus != "paid" && order.paymentStatus != "partialPayment" && order.paymentStatus != "partialRefunds")] disabled="disabled"[/#if] />
                <input type="button" id="returnsButton" class="button" value="${message("admin.order.returns")}"[#if order.expired || order.orderStatus != "confirmed" || (order.shippingStatus != "shipped" && order.shippingStatus != "partialShipment" && order.shippingStatus != "partialReturns")] disabled="disabled"[/#if] />
                <input type="button" id="cancelButton" class="button" value="${message("admin.order.cancel")}"[#if order.expired || order.orderStatus != "unconfirmed"] disabled="disabled"[/#if] />
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.sn")}:
            </th>
            <td width="360">
            ${order.sn}
            </td>
            <th>
            ${message("admin.common.createDate")}:
            </th>
            <td>
            ${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.orderStatus")}:
            </th>
            <td>
            ${message("Order.OrderStatus." + order.orderStatus)}
            [#if order.expired]
                <span title="${message("Order.expire")}: ${order.expire?string("yyyy-MM-dd HH:mm:ss")}">(${message("admin.order.hasExpired")})</span>
            [#elseif order.expire??]
                <span title="${message("Order.expire")}: ${order.expire?string("yyyy-MM-dd HH:mm:ss")}">(${message("Order.expire")}: ${order.expire})</span>
            [/#if]
            </td>
            <th>
            ${message("Order.paymentStatus")}:
            </th>
            <td>
            ${message("Order.PaymentStatus." + order.paymentStatus)}
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.category")}:
            </th>
            <td>
            [#if order.isSpecial == true]
                调机
            [#else ]
                任务
            [/#if]
            </td>
            <th>
            ${message("Member.username")}:
            </th>
            <td>
            ${order.customerId.realName}
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.amount")}:
            </th>
            <td>
            ${order.amount?string(',###')}元
            </td>
            <th>
            ${message("Order.amountPaid")}:
            </th>
            <td>
            ${order.amountPaid?string(',###')}元
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.offsetAmount")}:
            </th>
            <td>
            ${currency(order.offsetAmount, true)}
            </td>
            <th>
            ${message("Order.telephone")}:
            </th>
            <td>
            ${(order.telephone)}
            </td>
        </tr>
        <tr>
            <th>
            ${message("airplane.company")}:
            </th>
            <td>
            ${order.company.name}
            </td>
            <th>
            ${message("Order.airplane.type")}:
            </th>
            <td>
            ${order.airplane.typeId.typeName}
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.airplane.regNo")}:
            </th>
            <td>
            ${order.airplane.regNo}
            </td>
            <th>
            ${message("Order.paymentMethod")}:
            </th>
            <td>
            ${order.paymentMethodName}
            </td>
        </tr>
        <tr>
            <th>
            ${message("Order.Airline.firstTakeoffTime")}:
            </th>
            <td>
            ${order.firstTakeoffTime?string("yyyy-MM-dd HH:mm")}
            </td>
            <th>
            ${message("Order.Airline.lastTakeoffTime")}:
            </th>
            <td>
            ${order.lastTakeoffTime?string("yyyy-MM-dd HH:mm")}
            </td>
        </tr>
    </table>
    </br></br>
    <h3 style="padding-left: 100px">航段信息</h3>
    <table class="input" style="width: 750px;">
        <tbody>
        <tr>
            <th style="width: 300px;text-align:center">${message("OrderAirline.name")}</th>
            <th style="width: 150px;text-align:center">起飞时间</th>
            <th style="width: 150px;text-align:center">飞行时间</th>
            <th style="width: 150px;text-align:center">乘客数</th>
        </tr>
        [#list order.orderAirlines as orderAirlin]
        <tr style="text-align: center">
            <td>${orderAirlin.departure} - ${orderAirlin.destination}</td>
            <td>${orderAirlin.takeoffTime?string("yyyy-MM-dd HH:mm")}</td>
            <td>
                [#if orderAirlin.timeCost??]
                    [#if orderAirlin.timeCost?index_of(".")?number > -1]
                        [#if orderAirlin.timeCost?substring(0,orderAirlin.timeCost?index_of("."))?number > 0]
                        ${orderAirlin.timeCost?substring(0,orderAirlin.timeCost?index_of("."))}小时
                        [/#if]
                    ${("0." + orderAirlin.timeCost?substring(orderAirlin.timeCost?index_of(".") + 1))?number*60}分钟
                    [#else]
                    ${orderAirlin.timeCost}小时
                    [/#if]
                [/#if]
            </td>
            <td>${orderAirlin.passengerNum}</td>
        </tr>
        [/#list]
        </tbody>
    </table>
</div>
[#--航班信息--]
<div class="tabContent">
    <table class="input">
        <tr class="title">
            <th>
            ${message("OrderFlight.flightNo")}
            </th>
            <th>
            ${message("OrderAirline.name")}
            </th>
            <th>
            ${message("OrderFlight.boardingTime")}
            </th>
            <th>
            ${message("OrderFlight.boardingPlace")}
            </th>
        </tr>
    [#list order.orderFlights as orderFlight]
        <tr>
            <td>
            ${orderFlight.flightNo}
            </td>
            <td width="400">
            ${orderFlight.orderAirline.departure} - ${orderFlight.orderAirline.destination}
            </td>
            <td>
            ${orderFlight.boardingTime?string("yyyy-MM-dd HH:mm")}
            </td>
            <td>
            ${orderFlight.boardingPlace}
            </td>
        </tr>
    [/#list]
    </table>
    <div>
        <table class="input">

        </table>
    </div>
</div>
[#--收款信息--]
<table class="input tabContent">
    <tr class="title">
        <th>
        ${message("Payment.sn")}
        </th>
        <th>
        ${message("Payment.method")}
        </th>
        <th>
        ${message("Payment.paymentMethod")}
        </th>
        <th>
        ${message("Payment.amount")}
        </th>
        <th>
        ${message("Payment.status")}
        </th>
        <th>
        ${message("Payment.paymentDate")}
        </th>
    </tr>
[#list order.payments as payment]
    <tr>
        <td>
        ${payment.sn}
        </td>
        <td>
        ${message("Payment.Method." + payment.method)}
        </td>
        <td>
        ${(payment.paymentMethod)!"-"}
        </td>
        <td>
        ${currency(payment.amount, true)}
        </td>
        <td>
        ${message("Payment.Status." + payment.status)}
        </td>
        <td>
            [#if payment.paymentDate??]
                <span title="${payment.paymentDate?string("yyyy-MM-dd HH:mm:ss")}">${payment.paymentDate}</span>
            [#else]
                -
            [/#if]
        </td>
    </tr>
[/#list]
</table>
[#--发票信息--]
<table class="input tabContent">
[#--[#list order.shippings as shipping]--]
    <tr>
        <th>
        ${message("Order.invoiceTitle")}:
        </th>
        <td>
        ${(shipping.invoiceTitle)}
        </td>
        <th>
        ${message("Order.shippingMethod")}:
        </th>
        <td>
        ${shipping.shippingMethod}
        </td>
    </tr>
    <tr>
        <th>
        ${message("Shipping.trackingNo")}:
        </th>
        <td>
        ${shipping.trackingNo}
        </td>
        <th>
        ${message("Order.consignee")}:
        </th>
        <td>
        ${shipping.consignee}
        </td>

    </tr>
    <tr>
        <th>
        ${message("Order.area")}:
        </th>
        <td>
        ${shipping.area.fullName}
        </td>
        <th>
        ${message("Order.address")}:
        </th>
        <td>
        ${shipping.address}
        </td>

    </tr>
    <tr>
        <th>
        ${message("Order.phone")}:
        </th>
        <td>
        ${shipping.phone}
        </td>
        <th>
        ${message("Order.zipCode")}:
        </th>
        <td>
        ${shipping.zipCode}
        </td>
    </tr>
    <tr>
        <th>
        ${message("Order.memo")}:
        </th>
        <td colspan="3">
        ${shipping.memo}
        </td>
    </tr>
    <tr>
        <td></td><td></td><td></td>
        <td>
            <input type="button" id="shippingEdit" class="button shippingButton" value="${message("admin.common.edit")}"/>
        </td>
    </tr>
[#--[/#list]--]
</table>
[#--退款信息--]
<table class="input tabContent">
    <tr class="title">
        <th>
        ${message("Refunds.sn")}
        </th>
        <th>
        ${message("Refunds.method")}
        </th>
        <th>
        ${message("Refunds.paymentMethod")}
        </th>
        <th>
        ${message("Refunds.amount")}
        </th>
        <th>
        ${message("admin.common.createDate")}
        </th>
    </tr>
[#list order.refunds as refunds]
    <tr>
        <td>
        ${refunds.sn}
        </td>
        <td>
        ${message("Refunds.Method." + refunds.method)}
        </td>
        <td>
        ${refunds.paymentMethod!"-"}
        </td>
        <td>
        ${currency(refunds.amount, true)}
        </td>
        <td>
            <span title="${refunds.createDate?string("yyyy-MM-dd HH:mm:ss")}">${refunds.createDate}</span>
        </td>
    </tr>
[/#list]
</table>
[#--成交价及备注--]
<table class="input tabContent">
    <tr>
        <th>
        ${message("Order.rank")}:
        </th>
        <td>
            <textarea style="width:800px;height: 100px;padding: 5px;border: 1px solid #ddd;" readonly="readonly">${(order.rank)}</textarea>
        </td>
    </tr>
    <tr>
        <th>
        ${message("admin.order.memo")}:
        </th>
        <td>
            <textarea style="width: 800px;height: 150px;padding: 5px;border: 1px solid #ddd;">${order.memo}</textarea>
        </td>
    </tr>
</table>
[#--订单日志--]
<table class="input tabContent">
    <tr class="title">
        <th>
        ${message("OrderLog.type")}
        </th>
        <th>
        ${message("OrderLog.operator")}
        </th>
        <th>
        ${message("OrderLog.content")}
        </th>
        <th>
        ${message("admin.common.createDate")}
        </th>
    </tr>
[#list order.orderLogs as orderLog]
    <tr>
        <td>
        ${message("OrderLog.Type." + orderLog.type)}
        </td>
        <td>
            [#if orderLog.operator??]
            ${orderLog.operator}
            [#else]
                <span class="green">${message("admin.order.member")}</span>
            [/#if]
        </td>
        <td>
            [#if orderLog.content??]
                <span title="${orderLog.content}">${abbreviate(orderLog.content, 50, "...")}</span>
            [/#if]
        </td>
        <td>
            <span title="${orderLog.createDate?string("yyyy-MM-dd HH:mm:ss")}">${orderLog.createDate}</span>
        </td>
    </tr>
[/#list]
</table>
<table class="input">
    <tr>
        <th>
            &nbsp;
        </th>
        <td>
            <input type="button" class="button" value="${message("admin.common.back")}" onclick="location.href='list.jhtml'" />
        </td>
    </tr>
</table>
</body>
</html>
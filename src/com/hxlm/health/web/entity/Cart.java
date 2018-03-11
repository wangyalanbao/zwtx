/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.Setting;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateUtils;

/**
 * Entity - 购物车
 * 
 * 
 * 
 */
@Entity
@Table(name = "lm_cart")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_cart_sequence")
public class Cart extends BaseEntity {

	private static final long serialVersionUID = -6565967051825794561L;

	/** 超时时间 */
	public static final int TIMEOUT = 604800;

	/** 最大商品数 */
	public static final Integer MAX_PRODUCT_COUNT = 100;

	/** "ID"Cookie名称 */
	public static final String ID_COOKIE_NAME = "cartId";

	/** "密钥"Cookie名称 */
	public static final String KEY_COOKIE_NAME = "cartKey";

	/** 密钥 */
	private String key;

	/** 会员 */
	private Member member;

	/** 购物车项 */
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	/**
	 * 获取密钥
	 * 
	 * @return 密钥
	 */
	@Column(name = "cart_key", nullable = false, updatable = false)
	public String getKey() {
		return key;
	}

	/**
	 * 设置密钥
	 * 
	 * @param key
	 *            密钥
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@OneToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取购物车项
	 * 
	 * @return 购物车项
	 */
	@OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	/**
	 * 设置购物车项
	 * 
	 * @param cartItems
	 *            购物车项
	 */
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		int weight = 0;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					weight += cartItem.getWeight();
				}
			}
		}
		return weight;
	}

	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getQuantity() != null) {
					quantity += cartItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getPoint() {
		long point = 0L;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					point += cartItem.getPoint();
				}
			}
		}
		return point;
	}


	/**
	 * 获取商品价格
	 * 
	 * @return 商品价格
	 */
	@Transient
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getSubtotal() != null) {
					price = price.add(cartItem.getSubtotal());
				}
			}
		}
		return price;
	}








	/**
	 * 获取购物车项
	 * 
	 * @param product
	 *            商品
	 * @return 购物车项
	 */
	@Transient
	public CartItem getCartItem(Product product) {
		if (product != null && getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProduct() != null && cartItem.getProduct().equals(product)) {
					return cartItem;
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否包含商品
	 * 
	 * @param product
	 *            商品
	 * @return 是否包含商品
	 */
	@Transient
	public boolean contains(Product product) {
		if (product != null && getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProduct() != null && cartItem.getProduct().equals(product)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取令牌
	 * 
	 * @return 令牌
	 */
	@Transient
	public String getToken() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37).append(getKey());
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				hashCodeBuilder.append(cartItem.getProduct()).append(cartItem.getQuantity()).append(cartItem.getPrice());
			}
		}
		return DigestUtils.md5Hex(hashCodeBuilder.toString());
	}

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@Transient
	public boolean getIsLowStock() {
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getIsLowStock()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return new Date().after(DateUtils.addSeconds(getModifyDate(), TIMEOUT));
	}


	/**
	 * 判断是否为空
	 * 
	 * @return 是否为空
	 */
	@Transient
	public boolean isEmpty() {
		return getCartItems() == null || getCartItems().isEmpty();
	}


	/**
	 * 判断购物车里商品是否为同一品类下
	 *
	 * @return 是否同一品类
	 */
	@Transient
	public boolean isSameCategory () {
		List<Long> ids = new ArrayList<Long>();
		for (CartItem cartItem : getCartItems()) {
			Long cid = cartItem.getProduct().getProductCategory().getId();
			if (!ids.contains(cid)) {
				ids.add(cid);
			}
		}
		return ids.size() == 1 ? true : false;
	}
	/**
	 * 获取商品定价价格
	 *
	 * @return 商品价格
	 */
	@Transient
	public BigDecimal getShouldPrice() {
		BigDecimal price = new BigDecimal(0);
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getShouldSubtotal() != null) {
					price = price.add(cartItem.getShouldSubtotal());
				}
			}
		}
		return price;
	}
}
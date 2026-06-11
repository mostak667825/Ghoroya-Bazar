package com.example.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

// --------------------------------------------------------------------
// ENTITIES
// --------------------------------------------------------------------

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val id: String, // product id
    val name: String,
    val imageUrl: String,
    val price: Double,
    val memberPrice: Double,
    val unit: String,
    var quantity: Int = 1
)

@Entity(tableName = "member_profile")
data class MemberProfile(
    @PrimaryKey val id: Int = 1,
    val memberId: String = "GB-9080-24",
    val name: String = "মোঃ আবদুর রহমান",
    val savingsAmount: Double = 4350.0,
    val rewardPoints: Int = 1250,
    val ordersCount: Int = 12,
    val referralCount: Int = 8,
    val cashbackAmount: Double = 710.0,
    val isPaswoApplied: Boolean = false,
    val memberTier: String = "GOLD", // "SILVER", "GOLD", "PLATINUM"
    val paswoStatus: String = "NONE", // "NONE", "PENDING", "APPROVED", "REJECTED"
    val paswoQuotaRice: Int = 0,       // remaining kg limit
    val paswoQuotaOil: Int = 0,        // remaining litre limit
    val paswoQuotaLentils: Int = 0     // remaining kg limit
)

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val regularPrice: Double,
    val memberPrice: Double,
    val paswoPrice: Double, // Special even lower price for approved PASWO users
    val unit: String,
    val category: String,
    val imageType: String,
    val stock: Int = 50,
    val supplier: String = "রাজিবপুর কো-অপারেটিভ মিলস",
    val alertThreshold: Int = 10
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val customerName: String,
    val itemsSummary: String,
    val totalAmount: Double,
    val paymentChannel: String, // bKash, Nagad, Cash On Delivery
    val txId: String,
    val status: String, // PENDING, DISPATCHED, ON_THE_WAY, DELIVERED
    val address: String,
    val otp: String,
    val timestamp: Long
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val timestamp: String,
    val isRead: Boolean = false
)

@Entity(tableName = "paswo_applications")
data class PaswoApplicationEntity(
    @PrimaryKey val id: String, // match profile memberId
    val name: String,
    val mobile: String,
    val unionName: String, // Rajibpur, Mohanganj, Codimri, etc.
    val familySize: Int,
    val monthlyIncome: Double,
    val status: String // PENDING, APPROVED, REJECTED
)

// --------------------------------------------------------------------
// DAOS
// --------------------------------------------------------------------

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Update
    suspend fun updateCartItem(item: CartItem)

    @Delete
    suspend fun deleteCartItem(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM member_profile WHERE id = 1 LIMIT 1")
    fun getProfileFlow(): Flow<MemberProfile?>

    @Query("SELECT * FROM member_profile WHERE id = 1 LIMIT 1")
    suspend fun getProfileDirect(): MemberProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: MemberProfile)
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getProductsFlow(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products")
    suspend fun getAllProductsDirect(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("UPDATE products SET stock = :newStock WHERE id = :productId")
    suspend fun updateStock(productId: String, newStock: Int)

    @Query("UPDATE products SET memberPrice = :mPrice, regularPrice = :rPrice WHERE id = :productId")
    suspend fun updatePrices(productId: String, rPrice: Double, mPrice: Double)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProduct(productId: String)
}

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getOrdersFlow(): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("UPDATE orders SET status = :newStatus WHERE orderId = :ordId")
    suspend fun updateOrderStatus(ordId: String, newStatus: String)
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun getNotificationsFlow(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllAsRead()

    @Query("DELETE FROM notifications")
    suspend fun clearNotifications()
}

@Dao
interface PaswoApplicationDao {
    @Query("SELECT * FROM paswo_applications")
    fun getPaswoApplicationsFlow(): Flow<List<PaswoApplicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplication(app: PaswoApplicationEntity)

    @Query("UPDATE paswo_applications SET status = :status WHERE id = :appId")
    suspend fun updateApplicationStatus(appId: String, status: String)
}

@Entity(tableName = "erp_records")
data class ErpRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "STOCK_IN", "STOCK_OUT", "PURCHASE", "SALES"
    val productId: String,
    val productName: String,
    val quantity: Int,
    val costOrRevenue: Double,
    val counterparty: String, // Supplier or Buyer
    val timestamp: Long
)

@Entity(tableName = "user_sessions")
data class UserSessionEntity(
    @PrimaryKey val id: Int = 1,
    val phone: String = "",
    val name: String = "",
    val isLoggedIn: Boolean = false,
    val activeUnion: String = "Rajibpur"
)

@Dao
interface ErpRecordDao {
    @Query("SELECT * FROM erp_records ORDER BY timestamp DESC")
    fun getErpRecordsFlow(): Flow<List<ErpRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErpRecord(record: ErpRecordEntity)

    @Query("DELETE FROM erp_records")
    suspend fun clearErpRecords()
}

@Dao
interface UserSessionDao {
    @Query("SELECT * FROM user_sessions WHERE id = 1 LIMIT 1")
    fun getSessionFlow(): Flow<UserSessionEntity?>

    @Query("SELECT * FROM user_sessions WHERE id = 1 LIMIT 1")
    suspend fun getSessionDirect(): UserSessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: UserSessionEntity)
}

// --------------------------------------------------------------------
// ROOM DATABASE
// --------------------------------------------------------------------

@Database(
    entities = [
        CartItem::class,
        MemberProfile::class,
        ProductEntity::class,
        OrderEntity::class,
        NotificationEntity::class,
        PaswoApplicationEntity::class,
        ErpRecordEntity::class,
        UserSessionEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun profileDao(): ProfileDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun notificationDao(): NotificationDao
    abstract fun paswoDao(): PaswoApplicationDao
    abstract fun erpDao(): ErpRecordDao
    abstract fun sessionDao(): UserSessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ghoroya_bazar_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// --------------------------------------------------------------------
// MAIN REPOSITORY
// --------------------------------------------------------------------

class MainRepository(private val db: AppDatabase) {
    val cartItems: Flow<List<CartItem>> = db.cartDao().getCartItems()
    val memberProfile: Flow<MemberProfile?> = db.profileDao().getProfileFlow()
    val dbProducts: Flow<List<ProductEntity>> = db.productDao().getProductsFlow()
    val dbOrders: Flow<List<OrderEntity>> = db.orderDao().getOrdersFlow()
    val dbNotifications: Flow<List<NotificationEntity>> = db.notificationDao().getNotificationsFlow()
    val dbPaswoApps: Flow<List<PaswoApplicationEntity>> = db.paswoDao().getPaswoApplicationsFlow()
    val dbErpRecords: Flow<List<ErpRecordEntity>> = db.erpDao().getErpRecordsFlow()
    val userSession: Flow<UserSessionEntity?> = db.sessionDao().getSessionFlow()

    // Session operations
    suspend fun getOrCreateSession(): UserSessionEntity {
        var s = db.sessionDao().getSessionDirect()
        if (s == null) {
            s = UserSessionEntity()
            db.sessionDao().saveSession(s)
        }
        return s
    }

    suspend fun saveSession(session: UserSessionEntity) {
        db.sessionDao().saveSession(session)
    }

    // ERP logs operations
    suspend fun writeErpRecord(record: ErpRecordEntity) {
        db.erpDao().insertErpRecord(record)
    }

    // Cart operations
    suspend fun addToCart(item: CartItem) {
        db.cartDao().insertCartItem(item)
    }

    suspend fun updateCartQuantity(item: CartItem, newQty: Int) {
        if (newQty <= 0) {
            db.cartDao().deleteCartItem(item)
        } else {
            db.cartDao().updateCartItem(item.copy(quantity = newQty))
        }
    }

    suspend fun removeFromCart(item: CartItem) {
        db.cartDao().deleteCartItem(item)
    }

    suspend fun clearCart() {
        db.cartDao().clearCart()
    }

    // Profile operations
    suspend fun getOrCreateProfile(): MemberProfile {
        var profile = db.profileDao().getProfileDirect()
        if (profile == null) {
            profile = MemberProfile()
            db.profileDao().insertOrUpdateProfile(profile)
        }
        return profile
    }

    suspend fun updateProfile(profile: MemberProfile) {
        db.profileDao().insertOrUpdateProfile(profile)
    }

    suspend fun addSavings(amount: Double) {
        val currentProfile = getOrCreateProfile()
        updateProfile(currentProfile.copy(
            savingsAmount = currentProfile.savingsAmount + amount,
            rewardPoints = currentProfile.rewardPoints + (amount * 0.1).toInt()
        ))
    }

    suspend fun incrementOrders() {
        val currentProfile = getOrCreateProfile()
        updateProfile(currentProfile.copy(
            ordersCount = currentProfile.ordersCount + 1
        ))
    }

    suspend fun incrementReferrals() {
        val currentProfile = getOrCreateProfile()
        updateProfile(currentProfile.copy(
            referralCount = currentProfile.referralCount + 1,
            cashbackAmount = currentProfile.cashbackAmount + 100.0,
            rewardPoints = currentProfile.rewardPoints + 250
        ))
    }

    // Products operations
    suspend fun insertProducts(products: List<ProductEntity>) {
        db.productDao().insertProducts(products)
    }

    suspend fun insertProduct(product: ProductEntity) {
        db.productDao().insertProduct(product)
    }

    suspend fun updateProductStock(id: String, newStock: Int) {
        db.productDao().updateStock(id, newStock)
    }

    suspend fun editProductPrices(id: String, rPrice: Double, mPrice: Double) {
        db.productDao().updatePrices(id, rPrice, mPrice)
    }

    suspend fun deleteProduct(id: String) {
        db.productDao().deleteProduct(id)
    }

    // Order operations
    suspend fun createOrder(order: OrderEntity) {
        db.orderDao().insertOrder(order)
    }

    suspend fun updateOrderStatus(ordId: String, newStatus: String) {
        db.orderDao().updateOrderStatus(ordId, newStatus)
    }

    // Notification operations
    suspend fun triggerNotification(title: String, message: String) {
        val formattedTime = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())
        db.notificationDao().insertNotification(
            NotificationEntity(
                title = title,
                message = message,
                timestamp = formattedTime
            )
        )
    }

    suspend fun clearNotifications() {
        db.notificationDao().clearNotifications()
    }

    // PASWO operations
    suspend fun submitPaswoApp(app: PaswoApplicationEntity) {
        db.paswoDao().insertApplication(app)
        val profile = getOrCreateProfile()
        updateProfile(profile.copy(paswoStatus = "PENDING", isPaswoApplied = true))
    }

    suspend fun updatePaswoAppStatus(appId: String, status: String) {
        db.paswoDao().updateApplicationStatus(appId, status)
        if (appId == "GB-9080-24" || appId == "1") {
            val profile = getOrCreateProfile()
            val newQuotaRice = if (status == "APPROVED") 20 else 0
            val newQuotaOil = if (status == "APPROVED") 5 else 0
            val newQuotaLentils = if (status == "APPROVED") 5 else 0

            updateProfile(
                profile.copy(
                    paswoStatus = status,
                    paswoQuotaRice = newQuotaRice,
                    paswoQuotaOil = newQuotaOil,
                    paswoQuotaLentils = newQuotaLentils
                )
            )
        }
    }

    suspend fun consumePaswoQuota(riceKg: Int, oilLitre: Int, lentilsKg: Int) {
        val profile = getOrCreateProfile()
        updateProfile(
            profile.copy(
                paswoQuotaRice = (profile.paswoQuotaRice - riceKg).coerceAtLeast(0),
                paswoQuotaOil = (profile.paswoQuotaOil - oilLitre).coerceAtLeast(0),
                paswoQuotaLentils = (profile.paswoQuotaLentils - lentilsKg).coerceAtLeast(0)
            )
        )
    }
}

typealias Product = ProductEntity


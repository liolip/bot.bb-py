from telegram import Update
from telegram.ext import ApplicationBuilder, CommandHandler, ContextTypes
import random
import time

# Словарь для хранения общего объема спермы по пользователю
user_total = {}
# Словарь для хранения времени последнего вызова
user_last_time = {}

async def start(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await update.message.reply_text("Привет, напиши /ebembetmena, чтобы узнать сколько ты залил в бетмена 🍆")

async def ebembetmena(update: Update, context: ContextTypes.DEFAULT_TYPE):
    user_id = update.effective_user.id
    current_time = time.time()

    # Проверяем таймер — раз в 3600 секунд (1 час)
    if user_id in user_last_time and current_time - user_last_time[user_id] < 3600:
        remaining = int(3600 - (current_time - user_last_time[user_id]))
        minutes = remaining // 60
        seconds = remaining % 60
        await update.message.reply_text(f"Подожди ещё {minutes} мин. {seconds} сек. перед следующим заливом.")
        return

    # Генерация "размера"
    size = round(random.uniform(3.0, 25.0), 1)
    emojis = "🍆" * int(size // 3)

    # Обновление общего объема
    if user_id not in user_total:
        user_total[user_id] = 0
    user_total[user_id] += size
    total = round(user_total[user_id], 1)

    # Сохраняем время последнего вызова
    user_last_time[user_id] = current_time

    await update.message.reply_text(
        f"Ты залил в бетмена {size} л. спермы {emojis}\nВсего ты залил {total} л. 🧪"
    )

app = ApplicationBuilder().token("7356003536:AAF5LWvzC4DM9dngb1Ckfl6bhvihCvNWIC0").build()

app.add_handler(CommandHandler("start", start))
app.add_handler(CommandHandler("ebembetmena", ebembetmena))

app.run_polling()
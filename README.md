# L2Hostility Fabricated

*Current Version - 2.4.30*
*Current Commit - eb44a5c*

## 与官方版本的差异

- 内容包括L2扩充部分物品
- 目标Debuff类词条(TargetEffectTrait)：当目标已有的debuff数量多于potionTraitMaxEffect时不会生效(可配置)
- 榴弹词条：发射火球而不是潜影弹，命中时造成每级4点伤害
- 潜影词条：最大等级为5，命中时造成每级4点伤害
- 升空词条：效果时长默认为80tick而不是200tick
- 重力词条：迫降效果对末影龙无效
- 摄魂词条：攻击不仅穿透护甲并且穿透盾牌
- 反射词条：
    - 效果不同，默认情况下反射3格内的所有类型伤害，该范围可以配置，也可以配置是否反射魔法伤害，另外可以限制反射伤害不超过目标最大生命值的reflectLimit(默认为1.0)倍
    - 实现不同，此处的逻辑：
        - 对于有攻击特效的词条(重写了onHurting方法的MobTrait)，例如A攻击B，试图施加攻击特效(onHurting方法)
          时，先检查B是否可以反射该特效。如果可以，将该攻击特效作用于B周围(配置给定的范围内)所有不能反射该特效的实体{C}上(
          由于A并没有直接对C造成伤害，故此时onHurting方法内本次攻击伤害的修改是无效的)；如果不可以，则将攻击特效直接作用于B。
        - 对于效果光环类词条、推拉类词条、杀戮光环词条，施加效果前检查目标是否可以反射该词条，若可以反射则跳过。
- 摄魂词条：免疫一定比例的物理伤害，伤害穿透有冷却，可配置
- 破魔词条：免疫一定比例的魔法伤害，伤害穿透有冷却，封印的附魔数量为随机，最大数量正比于词条等级，可配置
- 魔法伤害类型标签：目前在Fabric侧支持SpellPower的伤害类型
- 不死词条：复活次数有限，次数正比于词条等级
- 诅咒效果：
    - 每级20%减疗，而不是100%，可配置
    - 诅咒之刃1~3级施加3~5级诅咒效果
    - 原有的普通和延时诅咒药水提升为4级，增加5级药水
    - 词条掉落普通4级药水
- 适应词条：
    - 适应减伤因子默认为0.7
    - 适应减伤因子有最小值，默认为0.05，可配置
- 复印词条：
    - 复印伤害加成默认根据魔咒等级而不是魔咒点数，可配置
    - 原配置reprintBypass机制(超过一定等级后添加虚空之触和消失诅咒)删除
    - 复印的魔咒总等级超过一定阈值后添加消失诅咒，可配置
- 诸神黄昏词条：封印有冷却，封印的物品数量为随机，最大数量正比于词条等级，可配置
- 虚空之触附魔：
    - 初始拥有一定概率(voidTouchChance)
    - 当伤害源本身穿透护甲(minecraft:bypass_armor)时，该概率增加(voidTouchChanceBonus)
    - 当伤害源本身穿透状态效果且穿透附魔(minecraft:bypass_effects/minecraft:bypass_enchantments)时，该概率增加(
      voidTouchChanceBonus)
    - 达到上述概率后，将该伤害源标记为真伤(minecraft:bypass_armor/minecraft:bypass_magic/minecraft:
      bypass_effects/minecraft:bypass_enchantments/minecraft:bypass_shield/minecraft:bypass_cooldown/minecraft:
      bypass_invulnerability)
- 剑风附魔：
    - 横扫范围Box在xyz正负方向扩展windSweepIncrement*附魔等级(格)
    - 实体攻击距离增加windSweepIncrement*附魔等级(格)
- 保险附魔：
    - 物品受到致命损耗时，在nbt内存入此时的破损度(previousDamage)，开始tick计时，消耗至剩余1点耐久
    - tick结束后(时长可配置)，如果此时物品破损度大于previousDamage，则移除保险附魔，否则移除tick
- 特殊火焰弹(包括魂炎弹、爆炎弹、黑炎弹、嗜魔弹、永恒嗜魔弹)：
    - 可以被反弹(原版恶意中无法反弹)
- 词条数量限制
    - 对应配置文件中的defaultTraitCountCap，默认为5
    - 对应数据包中的trait_count_cap字段，默认为5
    - 通过词条物品或者词条本身(如嗜魔/生长)添加的词条不受影响
    - 当佩戴深渊王座时无数量限制
- 词条兼容性方法(无法外部配置)
- 词条生成查看页面(REI/EMI)
- 部分词条的黑白名单不同
    - 反击：白名单从Warden和近战武器持有者(`l2hostility:melee_weapon_target`)改为无白名单
    - 排斥：白名单改为远程敌人(`l2hostility:ranged_enemy`)，包括原白名单和烈焰人、恶魂、潜影贝
    - 吸引：白名单近战武器持有者(`l2hostility:melee_weapon_target`)改为黑名单远程敌人(`l2hostility:ranged_enemy`)
    - 升空：白名单改为远程敌人(`l2hostility:ranged_enemy`)
- 支持在死亡消息中显示等级和词条信息(配置项deathMessageShowKillerTitle)

## 尚未实现的内容

- 移除侦测眼镜和封印物品在头部的渲染？(HeadFeatureRenderer)
- MASTER词条
- TF兼容
- JEI兼容
- 恶意信标
- 恶意刷怪笼
- L2C其余内容
- L2H其余新版内容
- 实体饰品配置界面

## 待修复

- 实体交互距离问题 (ReachEntityAttributes & PortingLibAttributes)
- EMI Untranslated Tag Warnings

## 一个关于数据包的细节问题

- 当试图用OpenLoader加载数据包以覆写模组内置的数据包时，有可能不起作用。
- 这个问题曾被我误以为是数据包加载顺序不对，重命名数据包 *(前缀Z)* 后仍不起效，后来发现与数据包加载顺序无关，这是由模组内部整理资源的方法导致的副作用。
- 比如说模组内置的对`minecraft:wither`的配置，这项配置对应的 **Identifier** 是`l2hostility:bosses`，要覆写这项配置，后来的
  **Identifier** 应该 **大于** `l2hostility:bosses`。
- **Identifier** 的比较方法是优先比较 **Path** ，当 **Path** 比较结果为相等时再比较 **Namespace** 。
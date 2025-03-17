# L2Hostility Fabricated

*Current Version - 2.4.29*
*Current Commit - e57a776*

## 与官方版本的差异

- 内容包括L2扩充部分物品
- 榴弹词条：发射火球而不是潜影弹，命中时造成每级4点伤害
- 潜影词条：最大等级为5，命中时造成每级4点伤害
- 升空词条效果时长默认为80tick而不是200tick
- 摄魂词条攻击不仅穿透护甲并且穿透盾牌
- 魔法伤害类型标签，目前在Fabric侧支持SpellPower的伤害类型
- 反射词条效果不同，默认情况下反射3格内的所有类型伤害，该范围可以配置，也可以配置是否反射魔法伤害
- 反射效果的实现不同，此处的逻辑：
    - 对于有攻击特效的词条(重写了onHurting方法的MobTrait)，例如A攻击B，试图施加攻击特效(onHurting方法)
      时，先检查B是否可以反射该特效。如果可以，将该攻击特效作用于B周围(配置给定的范围内)所有不能反射该特效的实体{C}上(
      由于A并没有直接对C造成伤害，故此时onHurting方法内本次攻击伤害的修改是无效的)；如果不可以，则将攻击特效直接作用于B。
    - 对于效果光环类词条、推拉类词条、杀戮光环词条，施加效果前检查目标是否可以反射该词条，若可以反射则跳过。
- 虚空之触附魔的实现不同，此处的逻辑：
    - 初始拥有一定概率(voidTouchChance)
    - 当伤害源本身穿透护甲(minecraft:bypass_armor)时，该概率增加(voidTouchChanceBonus)
    - 当伤害源本身穿透状态效果且穿透附魔(minecraft:bypass_effects/minecraft:bypass_enchantments)时，该概率增加(
      voidTouchChanceBonus)
    - 达到上述概率后，将该伤害源标记为真伤(minecraft:bypass_armor/minecraft:bypass_magic/minecraft:
      bypass_effects/minecraft:bypass_enchantments/minecraft:bypass_shield/minecraft:bypass_cooldown/minecraft:
      bypass_invulnerability)
- 剑风附魔的实现不同，此处的逻辑：
    - 横扫范围Box在xyz正负方向扩展windSweepIncrement*附魔等级(格)
    - 实体攻击距离增加windSweepIncrement*附魔等级(格)
- 保险附魔的效果以及实现不同，此处的逻辑：
    - 物品受到致命损耗时，在nbt内存入此时的破损度(previousDamage)，开始tick计时，消耗至剩余1点耐久
    - tick结束后(时长可配置)，如果此时物品破损度大于previousDamage，则移除保险附魔，否则移除tick
- 特殊火焰弹(包括魂炎弹、爆炎弹、黑炎弹、嗜魔弹、永恒嗜魔弹)部分逻辑不同
    - 可以被反弹(原版恶意中无法反弹)
- 词条数量限制
    - 对应配置文件中的defaultTraitCountCap，默认为5
    - 对应数据包中的trait_count_cap字段，默认为5
    - 通过词条物品或者词条本身(如嗜魔/生长)添加的词条不受影响
    - 当佩戴深渊王座时无数量限制
- 词条兼容性方法(无法外部配置)

## 尚未实现的内容 (按优先级排序)

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
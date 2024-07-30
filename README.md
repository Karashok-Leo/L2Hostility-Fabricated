# L2Hostility Fabricated

*Current Commit - bdae4c76d3265658cb227fde3f22a226ea3cc4a0*

## 与官方版本的差异
- 内容包括L2扩充部分物品
- 榴弹词条：发射火球而不是潜影弹
- 升空词条效果时长默认为80tick而不是200tick
- 摄魂词条攻击不仅穿透护甲并且穿透盾牌
- 魔法伤害类型标签，目前在Fabric侧支持SpellPower的伤害类型
- 反射效果的实现不同，此处的逻辑：
   - 对于有攻击特效的词条(重写了onHurting方法的MobTrait)，例如A攻击B，试图施加攻击特效(onHurting方法)时，先检查B是否可以反射该特效。如果可以，将该攻击特效作用于B周围(配置给定的范围内)所有不能反射该特效的实体{C}上(由于A并没有直接对C造成伤害，故此时onHurting方法内本次攻击伤害的修改是无效的)；如果不可以，则将攻击特效直接作用于B。
   - 对于效果光环类词条、推拉类词条、杀戮光环词条，施加效果前检查目标是否可以反射该词条，若可以反射则跳过。

## 尚未实现的内容 (按优先级排序)
- 恶意子弹实体 (子弹应该施加其他词条的效果而不是漂浮)
- JEI兼容
- TF兼容
- BoMD兼容
- L2扩充其余内容
- L2恶意新版内容
- L2C附魔配置 (WeaponConfig)
- 指令
- 恶意刷怪笼
- 实体饰品配置界面
- Hostility Charge Item

## 待修复
- 实体交互距离问题 (ReachEntityAttributes & PortingLibAttributes)
- EMI Untranslated Tag Warnings
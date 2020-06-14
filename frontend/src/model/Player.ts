import Card from './Card';

export default class Player {
  id!: string

  name!: string

  chips!: number

  bet!: number

  potContribution!: number

  chipsAwarded!: number

  pocketCards!: Card[]

  bestHand!: Card[] | null

  net!: number

  folded!: boolean

  totalBet!: number

  ready!: boolean

  broke!: boolean

  maxBet!: number

  tablePosition!: number
}

import Player from './Player';
import Card from './Card';

export default class Table {
    tableName!: string

    players!: Player[]

    boardCards!: Card[] | null

    button!: Player;

    actor!: Player;

    lastRaised!: Player

    winners!: Player[] | null

    pot!: number

    toCallAmount!: number

    newHandPlayers!: Player[]

    state!: string

    started!: boolean

    gameOver!: boolean

    gameWinner!: Player | null

    winningCards!: Card[]

    potSize!: number

    toRaiseAmount!: number

    stateDescription!: string

    winDueToFolding!: boolean

    potContribution!: number
}
